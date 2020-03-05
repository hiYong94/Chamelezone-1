package com.yeonae.chamelezone.view.course

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.PlaceItem
import com.yeonae.chamelezone.ext.shortToast
import com.yeonae.chamelezone.view.course.adapter.PlaceCheckRvAdapter
import com.yeonae.chamelezone.view.search.presenter.SearchContract
import com.yeonae.chamelezone.view.search.presenter.SearchPresenter
import kotlinx.android.synthetic.main.fragment_place_check_dialog.*
import kotlinx.android.synthetic.main.item_place_check.*

class PlaceCheckDialogFragment : DialogFragment(), SearchContract.View {
    override lateinit var presenter: SearchContract.Presenter
    private val placeChoiceRvAdapter = PlaceCheckRvAdapter()
    private var heightRatio = 0.8803125f  // default
    private var widthRatio = 0.88888889f // default
    private var onClickListener: OnClickListener? = null
    private lateinit var lastCheckedPlace: PlaceItem
    var checkStatus: Boolean = false

    interface OnClickListener {
        fun onClick(place: PlaceItem, placeIndex: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onClickListener = (context as OnClickListener)
    }

    override fun onStart() {
        super.onStart()
        val placeIndex = arguments?.getInt("placeIndex")
        val dpMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dpMetrics)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        arguments?.getFloat(DIALOG_HEIGHT_RATIO)?.let {
            if (it > 0) {
                heightRatio = it
            }
        }
        arguments?.getFloat(DIALOG_WIDTH_RATIO)?.let {
            if (it > 0) {
                widthRatio = it
            }
        }
        dialog?.window?.setLayout(
            (dpMetrics.widthPixels * widthRatio).toInt(),
            (dpMetrics.heightPixels * heightRatio).toInt()
        )

        presenter = SearchPresenter(
            Injection.placeRepository(), this
        )
        setAdapter()

        placeChoiceRvAdapter.setOnClickListener(object : PlaceCheckRvAdapter.OnClickListener {
            override fun onClick(place: PlaceItem) {
                Log.d("placeCheck", btn_check.isChecked.toString())
                if (btn_check.isChecked) {
                    lastCheckedPlace = place
                    checkStatus = true
                } else if (!btn_check.isChecked) {
                    checkStatus = false
                }
            }
        })

        btn_search.setOnClickListener {
            presenter.searchByName("${edt_search.text}")
        }

        edt_search.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_GO) {
                presenter.searchByName("${edt_search.text}")
            }
            true
        }

        btn_ok.setOnClickListener {
            if (checkStatus && placeIndex != null) {
                onClickListener?.onClick(lastCheckedPlace, placeIndex)
                dialog?.cancel()
            } else if (!checkStatus) {
                context?.shortToast(R.string.enter_place)
            }
        }

        btn_cancel.setOnClickListener {
            dialog?.cancel()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place_check_dialog, container, false)
    }

    override fun showPlaceList(placeList: List<PlaceItem>) {
        layout_no_search.visibility = View.GONE
        layout_has_search.visibility = View.VISIBLE
        placeChoiceRvAdapter.addData(placeList)
    }

    override fun showMessage(message: String) {
        layout_no_search.visibility = View.VISIBLE
        layout_has_search.visibility = View.GONE
        tv_message.text = message
    }

    private fun setAdapter() {
        recycler_place_choice.layoutManager = LinearLayoutManager(context)
        recycler_place_choice.adapter = placeChoiceRvAdapter
    }

    companion object {
        const val DIALOG_HEIGHT_RATIO = "DIALOG_HEIGHT_RATIO"
        const val DIALOG_WIDTH_RATIO = "DIALOG_WIDTH_RATIO"
        fun newInstance(placeIndex: Int): PlaceCheckDialogFragment {
            val frag = PlaceCheckDialogFragment()
            val args = Bundle()
            args.putInt("placeIndex", placeIndex)
            frag.arguments = args
            return frag
        }
    }
}

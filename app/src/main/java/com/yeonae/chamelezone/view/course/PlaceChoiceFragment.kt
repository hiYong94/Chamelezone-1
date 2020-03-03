package com.yeonae.chamelezone.view.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.SingleDialogFragment
import com.yeonae.chamelezone.data.model.PlaceItem
import com.yeonae.chamelezone.view.course.adapter.PlaceChoiceRvAdapter
import com.yeonae.chamelezone.view.search.presenter.SearchContract
import com.yeonae.chamelezone.view.search.presenter.SearchPresenter
import kotlinx.android.synthetic.main.fragment_place_choice.*

class PlaceChoiceFragment : Fragment(), SearchContract.View {
    override lateinit var presenter: SearchContract.Presenter
    private lateinit var lastCheckedPlace: PlaceItem
    private val placeChoiceRvAdapter = PlaceChoiceRvAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_place_choice, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = SearchPresenter(
            Injection.placeRepository(), this
        )
        setAdapter()

        placeChoiceRvAdapter.setOnClickListener(object : PlaceChoiceRvAdapter.OnClickListener {
            override fun onClick(place: PlaceItem) {
                lastCheckedPlace = place
            }
        })

        btn_ok.setOnClickListener {
            if (::lastCheckedPlace.isInitialized) {
                val placeIndex = arguments?.getInt(PLACE_INDEX)
                if (placeIndex != null) {
                    (activity as? CourseRegisterActivity)?.getVisible(
                        placeIndex,
                        lastCheckedPlace
                    )
                }
                requireActivity().onBackPressed()
            }

        }

        view?.setOnClickListener {
            true
        }

        btn_search.setOnClickListener {
            presenter.searchByName("${edt_search.text}")
        }

        btn_cancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

        edt_search.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_GO) {
                if ("${edt_search.text}".isEmpty()) {
                    showDialog()
                } else {
                    presenter.searchByName("${edt_search.text}")
                }
            }
            true
        }

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

    private fun showDialog() {
        val newFragment = SingleDialogFragment.newInstance(
            R.string.enter_search
        )
        fragmentManager?.let {
            newFragment.show(it, "dialog")
        }
    }

    companion object {
        private const val PLACE_INDEX = "placeIndex"

        fun newInstance(
            placeIndex: Int
        ) = PlaceChoiceFragment().apply {
            arguments = Bundle().apply {
                putInt(PLACE_INDEX, placeIndex)
            }

        }
    }
}
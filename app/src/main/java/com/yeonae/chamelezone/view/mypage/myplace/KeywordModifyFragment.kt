package com.yeonae.chamelezone.view.mypage.myplace

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.DialogFragment
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.shortToast
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.view.mypage.myplace.presenter.KeywordContract
import com.yeonae.chamelezone.view.mypage.myplace.presenter.KeywordPresenter
import kotlinx.android.synthetic.main.fragment_check_dialog.*

class KeywordModifyFragment : DialogFragment(), KeywordContract.View {
    private val keywordMap = hashMapOf<Int, String>()
    override lateinit var presenter: KeywordContract.Presenter
    private var heightRatio = 0.8803125f  // default
    private var widthRatio = 0.88888889f // default
    private var selectedKeyword = arrayListOf<String>()
    private var onClickListener: OnClickListener? = null
    private var keywords = mutableListOf<Int>()

    override fun showKeywordList(response: List<KeywordResponse>) {
        for (i in response.indices) {
            keywordMap[response[i].keywordNumber] = response[i].keywordName
        }
    }

    override fun showResult(response: Boolean) {
        if (response) {
            context?.shortToast(R.string.success_update_opening_hours)
            dialog?.cancel()
        }
    }

    interface OnClickListener {
        fun onClick(keywordList: ArrayList<String>)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as? OnClickListener).let {
            onClickListener = it
        }
    }

    override fun onStart() {
        super.onStart()
        presenter = KeywordPresenter(Injection.placeRepository(), this)
        presenter.getKeyword()

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

        val placeNumber = arguments?.getInt("placeNumber")
        val placeKeywordNumbers = arguments?.getIntegerArrayList("placeKeywordNumbers")
        val keyword = arguments?.getStringArrayList("keyword")
        selectedKeyword = arguments?.getStringArrayList("selectedKeyword") as ArrayList<String>

        if (keyword != null) {
            for (i in 0 until keyword.size) {
                addKeywordView(keyword[i])
            }
        }

        btn_ok.setOnClickListener {
            if (selectedKeyword.isEmpty()) {
                context?.shortToast(R.string.enter_place_keyword)
            } else {
                onClickListener?.onClick(selectedKeyword)
                keywords.clear()
                for (i in 0 until selectedKeyword.size) {
                    for (j in 0 until keywordMap.size) {
                        if (keywordMap[j] == selectedKeyword[i]) {
                            keywords.add(j)
                        }
                    }
                }
                if (placeNumber != null && placeKeywordNumbers != null) {
                    presenter.updateKeyword(placeNumber, keywords, placeKeywordNumbers)
                }
                dialog?.cancel()
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
        return inflater.inflate(R.layout.fragment_check_dialog, container, false)
    }

    private fun addKeywordView(keyword: String) {
        val layout = LayoutInflater.from(context)
            .inflate(R.layout.item_check_box, check_box, false)
        check_box.addView(layout)
        layout.findViewById<CheckBox>(R.id.cb_keyword).run {
            this.text = keyword
            selectedKeyword.forEach {
                if (it == keyword) {
                    this.isChecked = true
                }
            }
            setOnClickListener {
                if (this.isChecked) {
                    selectedKeyword.add(this.text.toString())
                } else {
                    selectedKeyword.remove(this.text.toString())
                }
            }
        }
    }

    companion object {
        const val DIALOG_HEIGHT_RATIO = "DIALOG_HEIGHT_RATIO"
        const val DIALOG_WIDTH_RATIO = "DIALOG_WIDTH_RATIO"
        fun newInstance(
            placeNumber: Int,
            placeKeywordNumbers: ArrayList<Int>,
            keywordList: ArrayList<String>,
            selectedKeyword: ArrayList<String>
        ): KeywordModifyFragment {
            val frag = KeywordModifyFragment()
            val args = Bundle()
            args.putInt("placeNumber", placeNumber)
            args.putIntegerArrayList("placeKeywordNumbers", placeKeywordNumbers)
            args.putStringArrayList("keyword", keywordList)
            args.putStringArrayList("selectedKeyword", selectedKeyword)
            frag.arguments = args
            return frag
        }
    }
}

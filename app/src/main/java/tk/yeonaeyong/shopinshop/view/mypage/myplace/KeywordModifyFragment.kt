package tk.yeonaeyong.shopinshop.view.mypage.myplace

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
import kotlinx.android.synthetic.main.fragment_check_dialog.*
import tk.yeonaeyong.shopinshop.Injection
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.ext.shortToast
import tk.yeonaeyong.shopinshop.network.model.KeywordResponse
import tk.yeonaeyong.shopinshop.view.mypage.myplace.presenter.KeywordContract
import tk.yeonaeyong.shopinshop.view.mypage.myplace.presenter.KeywordPresenter

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
        keywordMap.values.forEach {
            addKeywordView(it)
        }
    }

    override fun showResult(response: Boolean) {
        if (response) {
            context?.shortToast(R.string.success_update_keyword)
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
        selectedKeyword = arguments?.getStringArrayList("selectedKeyword") as ArrayList<String>

        btn_ok.setOnClickListener {
            if (selectedKeyword.isEmpty()) {
                context?.shortToast(R.string.enter_place_keyword)
            } else {
                onClickListener?.onClick(selectedKeyword)
                keywords.clear()
                for (i in 0 until selectedKeyword.size) {
                    for (j in 1..keywordMap.size) {
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
            selectedKeyword: ArrayList<String>
        ): KeywordModifyFragment {
            return KeywordModifyFragment().apply {
                arguments = Bundle().apply {
                    putInt("placeNumber", placeNumber)
                    putIntegerArrayList("placeKeywordNumbers", placeKeywordNumbers)
                    putStringArrayList("selectedKeyword", selectedKeyword)
                }
            }
        }
    }
}

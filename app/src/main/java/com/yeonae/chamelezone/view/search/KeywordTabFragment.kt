package com.yeonae.chamelezone.view.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.PlaceItem
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.view.place.PlaceDetailActivity
import com.yeonae.chamelezone.view.search.adapter.SearchRvAdapter
import com.yeonae.chamelezone.view.search.presenter.KeywordSearchContract
import com.yeonae.chamelezone.view.search.presenter.KeywordSearchPresenter
import kotlinx.android.synthetic.main.fragment_keyword_tab.*

class KeywordTabFragment : Fragment(), KeywordSearchContract.View {
    private val searchRvAdapter = SearchRvAdapter()
    override lateinit var presenter: KeywordSearchContract.Presenter
    private lateinit var listener: OnKeywordSelectedListener

    interface OnKeywordSelectedListener {
        fun keywordSelected(keyword: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = (context as OnKeywordSelectedListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_keyword_tab, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = KeywordSearchPresenter(
            Injection.placeRepository(), this
        )
        presenter.getKeyword()
        setAdapter()

        searchRvAdapter.setOnClickListener(object : SearchRvAdapter.OnClickListener {
            override fun onClick(place: PlaceItem) {
                val intent = Intent(requireContext(), PlaceDetailActivity::class.java)
                intent.putExtra(PLACE_NAME, place.name)
                intent.putExtra(PLACE_NUMBER, place.placeNumber)
                startActivity(intent)
            }
        })
    }

    override fun showPlaceList(placeList: List<PlaceItem>) {
        layout_no_search.visibility = View.GONE
        layout_has_search.visibility = View.VISIBLE
        searchRvAdapter.addData(placeList)
    }

    override fun showMessage(message: String) {
        layout_no_search.visibility = View.VISIBLE
        layout_has_search.visibility = View.GONE
        tv_message.text = message
    }

    private val keywordViewList by lazy {
        listOf(
            btn_keyword_1,
            btn_keyword_2,
            btn_keyword_3,
            btn_keyword_4,
            btn_keyword_5,
            btn_keyword_6,
            btn_keyword_7,
            btn_keyword_8,
            btn_keyword_9,
            btn_keyword_10
        )
    }

    override fun showKeywordList(response: List<KeywordResponse>) {
        keywordViewList.forEachIndexed { index, keywordView ->
            keywordView.text = response[index].keywordName
            keywordView.setOnClickListener {
                listener.keywordSelected(response[index].keywordName)
                searchByKeyword(response[index].keywordName)
            }
        }
    }

    private fun setAdapter() {
        recycler_keyword_tab.layoutManager = LinearLayoutManager(context)
        recycler_keyword_tab.adapter = searchRvAdapter
    }

    fun searchByKeyword(keyword: String) {
        presenter.searchByKeyword(keyword)
    }

    companion object {
        private const val PLACE_NAME = "placeName"
        private const val PLACE_NUMBER = "placeNumber"
        fun newInstance() = KeywordTabFragment()
    }
}
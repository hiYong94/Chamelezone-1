package com.yeonae.chamelezone.view.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Place
import com.yeonae.chamelezone.view.place.PlaceDetailActivity
import com.yeonae.chamelezone.view.search.adapter.SearchRvAdapter
import kotlinx.android.synthetic.main.fragment_keyword_tab.*

class KeywordTabFragment : Fragment() {
    private val searchList = arrayListOf(
        Place("구슬모아당구장", "전시회, 카페", "서울 용산구 독서당로 85", "7km"),
        Place("론리드프로젝트", "빨래방, 카페", "서울 용산구 신흥로 78", "10km"),
        Place(
            "하나은행X북바이북",
            "은행, 서점",
            "서울 종로구 새문안로5길 19",
            "13km"
        )
    )
    private val searchRvAdapter = SearchRvAdapter(searchList)

    private var listener: OnKeywordSelectedListener? = null

    interface OnKeywordSelectedListener {
        fun keywordSelected(keyword: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnKeywordSelectedListener
        if (listener == null) {
            throw ClassCastException("$context must implement OnArticleSelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_keyword_tab, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setAdapter()

        btn_cafe.setOnClickListener {
            listener?.keywordSelected("카페")
        }

        searchRvAdapter.setOnClickListener(object : SearchRvAdapter.OnClickListener {
            override fun onClick(place: Place) {
                val intent = Intent(context, PlaceDetailActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun setAdapter() {
        recycler_keyword_tab.layoutManager = LinearLayoutManager(context)
        recycler_keyword_tab.adapter = searchRvAdapter
    }
}
package com.yeonae.chamelezone.view.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Place
import com.yeonae.chamelezone.view.search.adapter.SearchRvAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setAdapter()

        searchRvAdapter.setOnClickListener(object : SearchRvAdapter.OnClickListener {
            override fun onClick(place: Place) {

            }
        })

        btn_back.setOnClickListener {
            finish()
        }

    }

    private fun setAdapter() {
        recycler_search.layoutManager = LinearLayoutManager(this)
        recycler_search.adapter = searchRvAdapter
    }
}
package com.yeonae.chamelezone.view.like

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.model.Like
import com.yeonae.chamelezone.model.Place
import com.yeonae.chamelezone.view.like.adapter.LikeTabRvAdapter
import kotlinx.android.synthetic.main.fragment_like_tab.*
import kotlinx.android.synthetic.main.item_like.*

class LikeTabFragment : Fragment() {
    private val likeList = arrayListOf(
        Place("구슬모아당구장", "전시회, 카페", "서울 용산구 독서당로 85"),
        Place("론리드프로젝트", "빨래방, 카페", "서울 용산구 신흥로 78"),
        Place("하나은행X북바이북", "은행, 서점", "서울 종로구 새문안로5길 19")
    )
    private val likeTabRvAdapter = LikeTabRvAdapter(likeList)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_like_tab, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setAdapter()

        likeTabRvAdapter.setOnClickListener(object : LikeTabRvAdapter.OnClickListener {
            override fun onClick(place: Place) {

            }
        })

//        btn_like.setOnClickListener {
//            btn_like.setImageResource(R.drawable.like01)
//        }

    }

    private fun setAdapter() {
        recycler_like.layoutManager = LinearLayoutManager(context)
        recycler_like.adapter = likeTabRvAdapter
    }
}

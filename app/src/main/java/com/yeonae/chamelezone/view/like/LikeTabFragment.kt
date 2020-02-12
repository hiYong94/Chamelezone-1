package com.yeonae.chamelezone.view.like

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Place
import com.yeonae.chamelezone.view.like.adapter.LikeTabRvAdapter
import com.yeonae.chamelezone.view.like.presenter.LikeContract
import com.yeonae.chamelezone.view.like.presenter.LikePresenter
import com.yeonae.chamelezone.view.login.LoginActivity
import com.yeonae.chamelezone.view.place.PlaceDetailActivity
import kotlinx.android.synthetic.main.fragment_like_tab.*

class LikeTabFragment : Fragment(), LikeContract.View {
    override lateinit var presenter: LikeContract.Presenter
    private val likeList = arrayListOf(
        Place("구슬모아당구장", "전시회, 카페", "서울 용산구 독서당로 85", "", "place1"),
        Place("론리드프로젝트", "빨래방, 카페", "서울 용산구 신흥로 78", "", "place2"),
        Place("하나은행X북바이북", "은행, 서점", "서울 종로구 새문안로5길 19", "", "place3")
    )
    private val likeTabRvAdapter = LikeTabRvAdapter(likeList)

    override fun showResultView(response: Boolean) {
        if (response) {
            layout_before_login.visibility = View.GONE
            layout_after_login.visibility = View.VISIBLE
            setAdapter()
            likeTabRvAdapter.setOnClickListener(object : LikeTabRvAdapter.OnClickListener {
                override fun onClick(place: Place) {
                    val intent = Intent(context, PlaceDetailActivity::class.java)
                    startActivity(intent)
                }
            })
        } else {
            layout_before_login.visibility = View.VISIBLE
            layout_after_login.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_like_tab, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = LikePresenter(
            Injection.memberRepository(requireContext()), this
        )
        btn_login.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setAdapter() {
        recycler_like.layoutManager = LinearLayoutManager(context)
        recycler_like.adapter = likeTabRvAdapter
    }

    override fun onResume() {
        super.onResume()
        presenter.checkLogin()
    }

}


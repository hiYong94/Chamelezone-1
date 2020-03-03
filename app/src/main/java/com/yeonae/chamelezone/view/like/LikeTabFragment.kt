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
import com.yeonae.chamelezone.ext.shortToast
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.view.like.adapter.LikeTabRvAdapter
import com.yeonae.chamelezone.view.like.presenter.LikeContract
import com.yeonae.chamelezone.view.like.presenter.LikePresenter
import com.yeonae.chamelezone.view.login.LoginActivity
import com.yeonae.chamelezone.view.place.PlaceDetailActivity
import kotlinx.android.synthetic.main.fragment_like_tab.*

class LikeTabFragment : Fragment(), LikeContract.View {
    override lateinit var presenter: LikeContract.Presenter
    private val likeTabRvAdapter = LikeTabRvAdapter()

    override fun showResultView(response: Boolean) {
        if (response) {
            layout_before_login.visibility = View.GONE
            layout_after_login.visibility = View.VISIBLE
            presenter.getUser()
        } else {
            layout_before_login.visibility = View.VISIBLE
            layout_after_login.visibility = View.GONE
        }
    }

    override fun showMyLikeList(response: List<PlaceResponse>) {
        likeTabRvAdapter.addData(response)
    }

    override fun showLikeState(response: Boolean) {
        if (response) {
            context?.shortToast(R.string.delete_like)
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
            Injection.memberRepository(requireContext()), Injection.likeRepository(), this
        )

        btn_login.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        setAdapter()
        likeTabRvAdapter.setOnClickListener(object : LikeTabRvAdapter.OnClickListener {
            override fun onClick(place: PlaceResponse) {
                val intent = Intent(context, PlaceDetailActivity::class.java)
                intent.putExtra(PLACE_NAME, place.name)
                intent.putExtra(PLACE_NUMBER, place.placeNumber)
                startActivity(intent)
            }
        })

        likeTabRvAdapter.setOnLikeClickListener(object : LikeTabRvAdapter.OnLikeClickListener {
            override fun onLikeClick(place: PlaceResponse) {
                place.memberNumber?.let {
                    presenter.deleteLike(place.likeStatus ?: 0,
                        it, place.placeNumber)
                }
            }
        })
    }

    private fun setAdapter() {
        recycler_like.layoutManager = LinearLayoutManager(context)
        recycler_like.adapter = likeTabRvAdapter
    }

    override fun onResume() {
        super.onResume()
        presenter.checkLogin()
    }

    companion object {
        private const val PLACE_NAME = "placeName"
        private const val PLACE_NUMBER = "placeNumber"
    }
}


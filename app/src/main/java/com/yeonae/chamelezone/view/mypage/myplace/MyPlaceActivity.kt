package com.yeonae.chamelezone.view.mypage.myplace

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.App
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.mypage.MoreButtonFragment
import com.yeonae.chamelezone.view.mypage.myplace.adapter.MyPlaceRvAdapter
import com.yeonae.chamelezone.view.mypage.myplace.presenter.MyPlaceContract
import com.yeonae.chamelezone.view.mypage.myplace.presenter.MyPlacePresenter
import com.yeonae.chamelezone.view.place.PlaceDetailActivity
import kotlinx.android.synthetic.main.activity_my_place.*

class MyPlaceActivity : AppCompatActivity(), MyPlaceContract.View {
    private val myPlaceRvAdapter = MyPlaceRvAdapter()
    override lateinit var presenter: MyPlaceContract.Presenter
    var memberNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_place)
        setAdapter()

        presenter = MyPlacePresenter(
            Injection.memberRepository(App.instance.context()), Injection.placeRepository(), this
        )

        presenter.getUser()

        myPlaceRvAdapter.setOnClickListener(object : MyPlaceRvAdapter.OnClickListener {
            override fun onClick(place: PlaceResponse) {
                val intent = Intent(this@MyPlaceActivity, PlaceDetailActivity::class.java)
                intent.putExtra(PLACE_NAME, place.name)
                intent.putExtra(PLACE_NUMBER, place.placeNumber)
                startActivity(intent)
            }
        })

        myPlaceRvAdapter.setMoreButtonListener(object : MyPlaceRvAdapter.MoreButtonListener {
            override fun bottomSheetDialog() {
                showBottomSheet()
            }
        })

        btn_back.setOnClickListener {
            finish()
        }

        btn_register.setOnClickListener {
            val intent = Intent(this, PlaceRegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun showUserInfo(user: UserEntity) {
        memberNumber = user.userNumber ?: 0
        presenter.getMyPlaceList(memberNumber)
    }

    override fun showMyPlaceList(response: List<PlaceResponse>) {
        layout_no_my_place.visibility = View.GONE
        layout_my_place.visibility = View.VISIBLE
        myPlaceRvAdapter.addData(response)
    }

    override fun showMessage(message: String) {
        layout_no_my_place.visibility = View.VISIBLE
        layout_my_place.visibility = View.GONE
        tv_message.text = message
    }

    private fun showBottomSheet() {
        val bottomSheetDialogFragment = MoreButtonFragment()
        bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
    }

    private fun setAdapter() {
        recycler_my_place.layoutManager = LinearLayoutManager(this)
        recycler_my_place.adapter = myPlaceRvAdapter
    }

    companion object {
        private const val PLACE_NAME = "placeName"
        private const val PLACE_NUMBER = "placeNumber"
    }
}
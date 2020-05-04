package com.yeonae.chamelezone.view.mypage.myplace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.PlaceItem
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.mypage.MoreButtonFragment
import com.yeonae.chamelezone.view.mypage.myplace.adapter.MyPlaceRvAdapter
import com.yeonae.chamelezone.view.mypage.myplace.presenter.MyPlaceContract
import com.yeonae.chamelezone.view.mypage.myplace.presenter.MyPlacePresenter
import com.yeonae.chamelezone.view.place.PlaceDetailActivity
import kotlinx.android.synthetic.main.activity_my_place.*

class MyPlaceActivity : AppCompatActivity(), MyPlaceContract.View,
    MoreButtonFragment.OnModifyClickListener, MoreButtonFragment.OnDeleteClickListener {
    private val myPlaceRvAdapter = MyPlaceRvAdapter()
    override lateinit var presenter: MyPlaceContract.Presenter
    var memberNumber: Int = 0
    lateinit var placeResponse: PlaceItem

    override fun showDeleteResult(response: Boolean) {
        if (response) {
            Log.d("placeDelete", response.toString())
        }
    }

    override fun onDeleteClick() {
        presenter.deletePlace(placeResponse.placeNumber, memberNumber)
        myPlaceRvAdapter.removeData(placeResponse)
    }

    override fun onModifyClick() {
        showPlaceModifyActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_place)
        setAdapter()

        presenter = MyPlacePresenter(
            Injection.memberRepository(), Injection.placeRepository(), this
        )

        presenter.getUser()

        myPlaceRvAdapter.setOnClickListener(object : MyPlaceRvAdapter.OnClickListener {
            override fun onClick(place: PlaceItem) {
                val intent = Intent(this@MyPlaceActivity, PlaceDetailActivity::class.java)
                intent.putExtra(PLACE_NAME, place.name)
                intent.putExtra(PLACE_NUMBER, place.placeNumber)
                startActivity(intent)
            }
        })

        myPlaceRvAdapter.setMoreButtonListener(object : MyPlaceRvAdapter.MoreButtonListener {
            override fun bottomSheetDialog(place: PlaceItem) {
                placeResponse = place
                showBottomSheet()
            }
        })

        btn_back.setOnClickListener {
            finish()
        }

        btn_register.setOnClickListener {
            val intent = Intent(this, PlaceRegisterActivity::class.java)
            startActivityForResult(intent, UPDATE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPDATE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                presenter.getMyPlaceList(memberNumber)
            }
        }
    }

    override fun showUserInfo(user: UserEntity) {
        memberNumber = user.userNumber ?: 0
        presenter.getMyPlaceList(memberNumber)
    }

    override fun showMyPlaceList(response: List<PlaceItem>) {
        tv_message.visibility = View.GONE
        recycler_my_place.visibility = View.VISIBLE
        myPlaceRvAdapter.addData(response)
    }

    override fun showMessage(message: String) {
        tv_message.visibility = View.VISIBLE
        recycler_my_place.visibility = View.GONE
        tv_message.text = message
    }

    private fun showPlaceModifyActivity() {
        val intent = Intent(this, PlaceModifyActivity::class.java)
        intent.putExtra("placeNumber", placeResponse.placeNumber)
        intent.putExtra("memberNumber", memberNumber)
        startActivityForResult(intent, UPDATE_REQUEST_CODE)
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
        const val UPDATE_REQUEST_CODE = 1
        private const val PLACE_NAME = "placeName"
        private const val PLACE_NUMBER = "placeNumber"
    }
}
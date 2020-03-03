package com.yeonae.chamelezone.view.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.adapter.ImageViewPagerAdapter
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE
import com.yeonae.chamelezone.ext.shortToast
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.login.LoginActivity
import com.yeonae.chamelezone.view.place.adapter.PlaceDetailPagerAdapter
import com.yeonae.chamelezone.view.place.presenter.PlaceDetailContract
import com.yeonae.chamelezone.view.place.presenter.PlaceDetailPresenter
import kotlinx.android.synthetic.main.activity_place_detail.*
import kotlin.math.abs

class PlaceDetailActivity : AppCompatActivity(), PlaceDetailContract.View {
    override lateinit var presenter: PlaceDetailContract.Presenter
    var memberNumber: Int? = null
    var placeNumber: Int = 0
    var placeName: String = ""
    var likeNumber: Int? = null

    override fun showLikeMessage(response: Boolean) {
        if (response) {
            this.shortToast(R.string.select_like)
        }
    }

    override fun showDeleteLikeMessage(response: Boolean) {
        if (response) {
            this.shortToast(R.string.delete_like)
        }
    }

    override fun placeInfo(place: PlaceResponse) {
        val placeImages = place.savedImageName.split(",")
        val images = arrayListOf<String>()
        for (i in placeImages.indices) {
            images.add(IMAGE_RESOURCE + placeImages[i])
        }
        likeNumber = place.likeStatus
        if (likeNumber != null) {
            btn_like.isChecked = true
        }
        val imageAdapter = ImageViewPagerAdapter(images)
        vp_image.adapter = imageAdapter

        if(likeNumber == null){
            btn_like.setOnClickListener {
                btn_like.isChecked = false
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
        } else if(likeNumber != null){
            btn_like.setOnClickListener {
                if (btn_like.isChecked) {
                    memberNumber?.let { it1 -> presenter.selectLike(it1, placeNumber) }
                } else {
                    memberNumber?.let { it1 -> presenter.deleteLike(likeNumber ?: 0, it1, placeNumber) }
                }
            }
        }
    }

    override fun deliverUserInfo(user: UserEntity) {
        memberNumber = user.userNumber
        presenter.placeDetail(placeNumber, memberNumber)
    }

    override fun showResultView(response: Boolean) {
        if (response) {
            presenter.getUser()

        } else {
            presenter.placeDetail(placeNumber, memberNumber)
        }
    }
  
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)

        val placeName = intent.getStringExtra(PLACE_NAME)
        placeNumber = intent.getIntExtra(PLACE_NUMBER, 0)
        tv_place_name.text = placeName
        tv_place_name_two.text = placeName

        presenter = PlaceDetailPresenter(
            Injection.memberRepository(applicationContext),
            Injection.placeRepository(),
            Injection.likeRepository(),
            this
        )
        presenter.checkLogin()

        btn_back.setOnClickListener {
            finish()
        }

        vp_image.post {
            vp_image.layoutParams = vp_image.layoutParams.apply {
                height = ((vp_image.parent as ViewGroup).width / 3) * 2
            }

            setupView()
        }

    }

    private fun setupView() {
        tab_layout.setupWithViewPager(vp_image, true)

        val fragmentAdapter =
            PlaceDetailPagerAdapter(supportFragmentManager, placeNumber, placeName, memberNumber)
        viewpager_detail.adapter = fragmentAdapter
        tabs_detail.setupWithViewPager(viewpager_detail)

        tool_bar.run {
            post {
                val nameBar = layout_visibility.height
                val tabBar = tabs_detail.height
                val density = resources.displayMetrics.density

                Log.d("PlaceDetailActivity nameBar", nameBar.toString())
                Log.d("PlaceDetailActivity tabBar", tabBar.toString())

                layoutParams = tool_bar.layoutParams.apply {
                    height = nameBar + tabBar
                }

                app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                    if (abs(verticalOffset) - appBarLayout.totalScrollRange > 0) {
                        layout_visibility.visibility = View.VISIBLE
                    } else {
                        Log.d("PlaceDetailActivity nameBar", nameBar.toString())
                        layout_visibility.visibility = View.GONE
                    }
                })
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        presenter.checkLogin()
    }

    companion object {
        private const val PLACE_NAME = "placeName"
        private const val PLACE_NUMBER = "placeNumber"
    }
}
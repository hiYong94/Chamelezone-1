package com.yeonae.chamelezone.view.review

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.kroegerama.kaiteki.toast
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.catchFocus
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.Context.APPLICATION_CONTEXT
import com.yeonae.chamelezone.view.review.presenter.ReviewContract
import com.yeonae.chamelezone.view.review.presenter.ReviewPresenter
import kotlinx.android.synthetic.main.activity_review_create.*
import kotlinx.android.synthetic.main.slider_item_image.view.*

class ReviewCreateActivity : AppCompatActivity(), BottomSheetImagePicker.OnImagesSelectedListener,
    ReviewContract.View {
    override lateinit var presenter: ReviewContract.Presenter
    private val uriList = arrayListOf<String>()
    var memberNumber = 0

    override fun review(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        finish()
    }

    override fun getMember(user: UserEntity) {
        memberNumber = user.userNumber ?: 0
    }

    override fun getMemberCheck(response: Boolean) {
        presenter.getMember()
    }

    override fun onImagesSelected(uris: List<Uri>, tag: String?) {
        toast("$tag")
//        image_container.removeAllViews()
        uris.forEachIndexed { index, uri ->
            val rl = LayoutInflater.from(this).inflate(
                R.layout.slider_item_image,
                image_container,
                false
            ) as RelativeLayout

            if (image_container.childCount < 4) {
                image_container.addView(rl)
                rl.image_item.run {
                    glideImageSet(uri, measuredWidth, measuredHeight)
                }
                Log.d("childCount", (image_container.childCount).toString())
            }

            rl.btn_delete.setOnClickListener {
                image_container.removeView(rl)
            }

            uri.path?.let { uriList.add(it) }
            Log.d("dddd", uri.path)
            Log.d("dddd childCount uriList", uriList[index])

        }
        Log.d("uriList", uriList.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_create)

        setupGUI()

        presenter = ReviewPresenter(
            Injection.reviewRepository(), Injection.memberRepository(APPLICATION_CONTEXT), this
        )

        presenter.checkMember()

        val placeNumber = intent.getIntExtra(PLACE_NUMBER, 0)
        Log.d("placeNumber", placeNumber.toString())

        btn_register.setOnClickListener {
            val content = "${edt_review.text}"


            presenter.reviewCreate(memberNumber, placeNumber, content, uriList)
            Log.d("reviewCreate memberNumber", memberNumber.toString())

            Log.d("uriList", uriList.toString())
        }
    }

    private var isCreated = false

    private val permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            Toast.makeText(this@ReviewCreateActivity, "권한이 허용되었습니다", Toast.LENGTH_SHORT).show()
            pickMulti()
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            isCreated = false
            Toast.makeText(
                this@ReviewCreateActivity,
                "권한이 거부되었습니다\n$deniedPermissions",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun pickMulti() {
        BottomSheetImagePicker.Builder(getString(R.string.file_provider))
            .multiSelect(2, 4)
            .multiSelectTitles(
                R.plurals.pick_multi,
                R.plurals.pick_multi_more,
                R.string.pick_multi_limit
            )
            .peekHeight(R.dimen.peekHeight)
            .columnSize(R.dimen.columnSize)
            .requestTag("사진이 선택되었습니다.")
            .show(supportFragmentManager)
    }

    private fun setupGUI() {
        tv_title.text = intent.getStringExtra(PLACE_NAME)
        tv_title.catchFocus()
        btn_back.setOnClickListener {
            finish()
        }
        btn_image_create.setOnClickListener {
            if (!isCreated) {
                isCreated = true
                checkPermission()
            }
            Handler().postDelayed({
                isCreated = false
            }, 1000)
        }
        btn_image_clear.setOnClickListener { image_container.removeAllViews() }
    }

    private fun checkPermission() {
        TedPermission.with(this)
            .setPermissionListener(permissionListener)
            .setRationaleTitle(R.string.rationale_title)
            .setRationaleMessage(R.string.album_rationale_message)
            .setDeniedTitle(R.string.Permission_denied)
            .setDeniedMessage(R.string.permission_msg)
            .setGotoSettingButtonText(R.string.setting)
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .check()
    }

    companion object {
        private const val PLACE_NUMBER = "placeNumber"
        private const val PLACE_NAME = "placeName"
    }
}
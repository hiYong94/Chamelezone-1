package com.yeonae.chamelezone.view.review

import android.Manifest
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.catchFocus
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.util.Logger
import com.yeonae.chamelezone.view.review.presenter.ReviewContract
import com.yeonae.chamelezone.view.review.presenter.ReviewPresenter
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import kotlinx.android.synthetic.main.activity_review_create.*

class ReviewCreateActivity : AppCompatActivity(),
    ReviewContract.View {
    override lateinit var presenter: ReviewContract.Presenter
    private val uriList = arrayListOf<String>()
    private var selectedUriList: List<Uri>? = null
    private var isCreated = false
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

    private fun showMultiImage(uris: List<Uri>) {
        this.selectedUriList = uris
        Logger.d("ted uriList: $uriList")

        image_container.removeAllViews()

        uris.forEachIndexed { _, uri ->
            val iv = LayoutInflater.from(this).inflate(
                R.layout.slider_item_image,
                image_container,
                false
            ) as ImageView
            iv.run {
                glideImageSet(uri, measuredWidth, measuredHeight)
                image_container.addView(iv)
            }
            uri.path?.let { uriList.add(it) }
            Logger.d("ted uriList2: $uriList")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_create)

        setupGUI()

        presenter = ReviewPresenter(
            Injection.reviewRepository(), Injection.memberRepository(), this
        )

        presenter.checkMember()

        val placeNumber = intent.getIntExtra(PLACE_NUMBER, 0)

        btn_register.setOnClickListener {
            val content = "${edt_review.text}"

            if (!isCreated) {
                isCreated = true
                presenter.reviewCreate(memberNumber, placeNumber, content, uriList)
                Handler().postDelayed({
                    isCreated = false
                }, 5000)
            }
        }
    }

    private val permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            val prefs: SharedPreferences =
                this@ReviewCreateActivity.getSharedPreferences("Pref", MODE_PRIVATE)
            val isFirstRun = prefs.getBoolean("isFirstRun", true)
            if (isFirstRun) {
                Toast.makeText(this@ReviewCreateActivity, "권한이 허용되었습니다", Toast.LENGTH_SHORT).show()
                prefs.edit().putBoolean("isFirstRun", false).apply()
            }
            setNormalMultiButton()
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

    private fun setNormalMultiButton() {
        TedImagePicker.with(this)
            .mediaType(MediaType.IMAGE)
            .min(2, R.string.min_msg)
            .max(4, R.string.max_msg)
            .errorListener { message -> Log.d("ted", "message: $message") }
            .selectedUri(selectedUriList)
            .startMultiImage { list: List<Uri> -> showMultiImage(list) }

    }

    private fun setupGUI() {
        tv_title.text = intent.getStringExtra(PLACE_NAME)
        tv_title.catchFocus()
        ll_touch.run { setOnClickListener { catchFocus() } }
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

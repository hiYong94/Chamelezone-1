package com.yeonae.chamelezone.view.review

import android.Manifest
import android.app.Activity
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.*
import com.yeonae.chamelezone.ext.Millisecond.ONE_SECOND
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.review.presenter.ReviewContract
import com.yeonae.chamelezone.view.review.presenter.ReviewPresenter
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import kotlinx.android.synthetic.main.activity_review_create.*
import kotlinx.android.synthetic.main.slider_item_image.view.*

class ReviewCreateActivity :
    AppCompatActivity(),
    ReviewContract.View {
    override lateinit var presenter: ReviewContract.Presenter
    private var uriList = ArrayList<String>()
    private var uriDataList = arrayListOf<String>()
    private var selectedUriList = mutableListOf<Uri>()
    private var isCreated = false
    private var isChecked = false
    private var memberNumber = 0

    override fun review(message: String) {
        isCreated = false
        shortToast(R.string.review_create_msg)
        hideLoading()
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun getMember(user: UserEntity) {
        memberNumber = user.userNumber ?: 0
    }

    override fun getMemberCheck(response: Boolean) {
        presenter.getMember()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_create)

        setupGUI()

        presenter = ReviewPresenter(
            Injection.reviewRepository(),
            Injection.memberRepository(), this
        )

        presenter.checkMember()

        val placeNumber = intent.getIntExtra(PLACE_NUMBER, 0)

        btn_register.setOnClickListener {
            val content = "${edt_review.text}"
            when {
                edt_review.text.isEmpty() || edt_review.text.isBlank() -> shortToast(R.string.review_content)
                uriList.isEmpty() -> shortToast(R.string.review_image)
                else -> {
                    showLoading()
                    if (!isCreated) {
                        isCreated = true
                        presenter.reviewCreate(
                            memberNumber,
                            placeNumber,
                            content,
                            uriList
                        )
                    }
                }
            }
        }
    }

    private fun setupGUI() {
        tv_place_name.text = intent.getStringExtra(PLACE_NAME)
        tv_place_name.catchFocus()
        ll_touch.run { setOnClickListener { catchFocus() } }
        btn_back.setOnClickListener {
            finish()
        }
        btn_image_create.setOnClickListener {
            if (!isChecked) {
                isChecked = true
                checkPermission()
            }
            Handler().postDelayed({
                isChecked = false
            }, ONE_SECOND)
        }
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

    private val permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            val prefs: SharedPreferences =
                this@ReviewCreateActivity.getSharedPreferences("pref", MODE_PRIVATE)
            val isFirstRun = prefs.getBoolean("isFirstRun", true)
            if (isFirstRun) {
                Toast.makeText(
                    this@ReviewCreateActivity,
                    R.string.permission_granted,
                    Toast.LENGTH_SHORT
                ).show()
                prefs.edit().putBoolean("isFirstRun", false).apply()
            }
            setNormalMultiButton()
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            isCreated = false
            Toast.makeText(
                this@ReviewCreateActivity,
                R.string.permission_denied,
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun setNormalMultiButton() {
        TedImagePicker.with(this)
            .mediaType(MediaType.IMAGE)
            .min(1, R.string.min_msg)
            .max(4, R.string.max_msg)
            .errorListener { message -> Log.d("ted", "message: $message") }
            .selectedUri(selectedUriList)
            .startMultiImage { list: List<Uri> -> showMultiImage(list) }
    }

    private fun showMultiImage(uris: List<Uri>) {
        if (uriDataList.count() != 0) {
            uriDataList.clear()
        }
        this.selectedUriList = uris.toMutableList()

        image_container.removeAllViews()

        uris.forEachIndexed { _, uri ->
            val rl = LayoutInflater.from(this).inflate(
                R.layout.slider_item_image,
                image_container,
                false
            ) as ConstraintLayout
            image_container.addView(rl)
            rl.image_item.run {
                glideImageSet(uri, measuredWidth, measuredHeight)
            }

            rl.btn_delete.setOnClickListener {
                image_container.removeView(rl)
                if (this.selectedUriList.count() != 0)
                    this.selectedUriList.remove(uri)
                if (uriList.count() != 0) {
                    uriList.remove(uri.path)
                }
            }

            btn_image_clear.setOnClickListener {
                image_container.removeAllViews()
                if (this.selectedUriList.count() != 0)
                    this.selectedUriList.removeAll(uris)
                if (uriList.count() != 0) {
                    uriList.clear()
                }
            }

            uri.path?.let { uriDataList.add(it) }
            val distinctData = uriDataList.distinct()
            uriList = ArrayList(distinctData)
        }
    }

    companion object {
        private const val PLACE_NUMBER = "placeNumber"
        private const val PLACE_NAME = "placeName"
    }
}

package com.yeonae.chamelezone.view.review

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.ReviewItem
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.ext.shortToast
import com.yeonae.chamelezone.view.mypage.myreview.MyReviewActivity
import com.yeonae.chamelezone.view.review.presenter.ReviewModifyContract
import com.yeonae.chamelezone.view.review.presenter.ReviewModifyPresenter
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import kotlinx.android.synthetic.main.activity_review_modify.*
import kotlinx.android.synthetic.main.slider_item_image.view.*

class ReviewModifyActivity :
    AppCompatActivity(),
    ReviewModifyContract.View {
    override lateinit var presenter: ReviewModifyContract.Presenter
    private var uriList = ArrayList<String>()
    private var uriDataList = arrayListOf<String>()
    private var selectedUriList = mutableListOf<Uri>()
    private var isCreated = false
    private var isChecked = false
    private var placeNumber = 0
    private var memberNumber = 0
    private var reviewNumber = 0
    private var imageNumber = arrayListOf<Int>()
    private var deleteImageNumber = ArrayList<Int>()

    override fun reviewModify(response: Boolean) {
        if (response) {
            shortToast(R.string.review_modify_msg)
            val intent = Intent(this, MyReviewActivity::class.java)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun showReview(review: ReviewItem) {
        imageNumber = review.imageNumber
        tv_title.text = review.name
        edt_review.text = SpannableStringBuilder(review.content)
        review.images.forEachIndexed { index, image ->
            val rl = LayoutInflater.from(this).inflate(
                R.layout.slider_item_image,
                image_container,
                false
            ) as RelativeLayout
            image_container.addView(rl)
            rl.image_item.run {
                glideImageSet(IMAGE_RESOURCE + image, measuredWidth, measuredHeight)
            }

            rl.btn_delete.setOnClickListener {
                image_container.removeView(rl)
                deleteImageNumber.add(imageNumber[index])
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_review_modify)

        tv_title.requestFocus()
        btn_back.setOnClickListener {
            finish()
        }
        setupGUI()

        presenter = ReviewModifyPresenter(
            Injection.reviewRepository(), this
        )

        placeNumber = intent.getIntExtra(PLACE_NUMBER, 0)
        memberNumber = intent.getIntExtra(MEMBER_NUMBER, 0)
        reviewNumber = intent.getIntExtra(REVIEW_NUMBER, 0)

        presenter.getReview(placeNumber, reviewNumber)

        btn_review_modify.setOnClickListener {
            val content = "${edt_review.text}"
            if (!isCreated) {
                isCreated = true
                presenter.modifyReview(
                    uriList,
                    reviewNumber,
                    memberNumber,
                    placeNumber,
                    content,
                    deleteImageNumber
                )
                Handler().postDelayed({
                    isCreated = false
                }, 5000)
            }

        }
    }

    private fun setupGUI() {
        btn_image_create.setOnClickListener {
            if (!isChecked) {
                isChecked = true
                checkPermission()
            }
            Handler().postDelayed({
                isChecked = false
            }, 1000)
        }
        btn_clear.setOnClickListener { image_container.removeAllViews() }
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
                this@ReviewModifyActivity.getSharedPreferences("pref", MODE_PRIVATE)
            val isFirstRun = prefs.getBoolean("isFirstRun", true)
            if (isFirstRun) {
                Toast.makeText(
                    this@ReviewModifyActivity,
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
                this@ReviewModifyActivity,
                "${R.string.permission_denied}\n$deniedPermissions",
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
        this.selectedUriList = uris.toMutableList()

        uris.forEachIndexed { index, uri ->
            val rl = LayoutInflater.from(this).inflate(
                R.layout.slider_item_image,
                image_container,
                false
            ) as RelativeLayout

            rl.image_item.run {
                glideImageSet(uri, measuredWidth, measuredHeight)
                image_container.addView(rl)
            }

            rl.btn_delete.setOnClickListener {
                image_container.removeView(rl)
                if (this.selectedUriList.count() != 0)
                    this.selectedUriList.removeAt(index)
            }

            btn_clear.setOnClickListener {
                image_container.removeAllViews()
                if (this.selectedUriList.count() != 0)
                    this.selectedUriList.removeAll(uris)
            }

            uri.path?.let { uriDataList.add(it) }
            val distinctData = uriDataList.distinct()
            uriList = ArrayList(distinctData)
        }
    }

    companion object {
        const val PLACE_NAME = "placeName"
        const val PLACE_NUMBER = "placeNumber"
        const val MEMBER_NUMBER = "memberNumber"
        const val REVIEW_NUMBER = "reviewNumber"
    }
}
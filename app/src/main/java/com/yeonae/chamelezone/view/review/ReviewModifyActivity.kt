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
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.ReviewItem
import com.yeonae.chamelezone.ext.Millisecond.ONE_SECOND
import com.yeonae.chamelezone.ext.Millisecond.THREE_SECOND
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.ext.hideLoading
import com.yeonae.chamelezone.ext.shortToast
import com.yeonae.chamelezone.ext.showLoading
import com.yeonae.chamelezone.util.Logger
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
    private var imageNumbers = arrayListOf<Int>()
    private var deleteImageNumber = ArrayList<Int>()
    private var savedImages = arrayListOf<String>()

    override fun reviewModify(response: Boolean) {
        if (response) {
            isCreated = false
            shortToast(R.string.review_modify_msg)
            hideLoading()
            val intent = Intent(this, MyReviewActivity::class.java)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun showReview(review: ReviewItem) {
        imageNumbers = review.imageNumber
        tv_place_name.text = review.name
        edt_review.text = SpannableStringBuilder(review.content)
        review.images.forEachIndexed { index, image ->
            savedImages.add(image)
            val rl = LayoutInflater.from(this).inflate(
                R.layout.slider_item_image,
                image_container,
                false
            ) as ConstraintLayout
            image_container.addView(rl)
            rl.image_item.run {
                glideImageSet(IMAGE_RESOURCE + image, measuredWidth, measuredHeight)
            }

            rl.btn_delete.setOnClickListener {
                image_container.removeView(rl)
                deleteImageNumber.add(imageNumbers[index])
                savedImages.remove(image)
            }
            btn_clear.setOnClickListener {
                image_container.removeAllViews()
                deleteImageNumber = imageNumbers
                if (savedImages.count() != 0)
                    savedImages.clear()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_review_modify)

        tv_place_name.requestFocus()
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

        btn_modify.setOnClickListener {
            val content = "${edt_review.text}"
            when {
                edt_review.text.isEmpty() || edt_review.text.isBlank() -> shortToast(R.string.review_content)
                uriList.isEmpty() && savedImages.isEmpty() -> shortToast(R.string.review_image)
                ((imageNumbers.count() - deleteImageNumber.count()) + uriList.count() > 4) -> shortToast(R.string.review_image_max)
                else -> {
                    showLoading()
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
                    }
                }
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
            }, ONE_SECOND.toLong())
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
        if (uriDataList.count() != 0) {
            uriDataList.clear()
        }
        this.selectedUriList = uris.toMutableList()

        uris.forEachIndexed { _, uri ->
            val rl = LayoutInflater.from(this).inflate(
                R.layout.slider_item_image,
                image_container,
                false
            ) as ConstraintLayout

            rl.findViewById<ImageView>(R.id.image_item).run {
                glideImageSet(uri, measuredWidth, measuredHeight)
                image_container.addView(rl)
            }

            rl.findViewById<ImageButton>(R.id.btn_delete).setOnClickListener {
                image_container.removeView(rl)
                if (this.selectedUriList.count() != 0)
                    this.selectedUriList.remove(uri)
                if (uriList.count() != 0) {
                    uriList.remove(uri.path)
                }
            }

            btn_clear.setOnClickListener {
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
        const val PLACE_NAME = "placeName"
        const val PLACE_NUMBER = "placeNumber"
        const val MEMBER_NUMBER = "memberNumber"
        const val REVIEW_NUMBER = "reviewNumber"
    }
}
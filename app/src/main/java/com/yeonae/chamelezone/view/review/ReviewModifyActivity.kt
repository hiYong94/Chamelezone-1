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
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.ReviewItem
import com.yeonae.chamelezone.ext.Millisecond.ONE_SECOND
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.ext.hideLoading
import com.yeonae.chamelezone.ext.shortToast
import com.yeonae.chamelezone.ext.showLoading
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
    private val uriSet = mutableSetOf<Uri>()
    private var isCreated = false
    private var isChecked = false
    private var placeNumber = 0
    private var memberNumber = 0
    private var reviewNumber = 0
    private var imageNumbers = arrayListOf<Int>()
    private var deleteImageNumber = ArrayList<Int>()
    private var savedImages = arrayListOf<String>()
    private var max = 4
    private var min = 1

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

        btn_clear.setOnClickListener {
            image_container.removeAllViews()
            uriSet.clear()
            deleteImageNumber = imageNumbers
            savedImages.clear()
        }

        btn_modify.setOnClickListener {
            val content = "${edt_review.text}"
            when {
                edt_review.text.isEmpty() || edt_review.text.isBlank() -> shortToast(R.string.review_content)
                uriSet.isEmpty() && savedImages.isEmpty() -> shortToast(R.string.review_image)
                ((imageNumbers.count() - deleteImageNumber.count()) + uriSet.count() > 4) -> shortToast(
                    R.string.review_image_max
                )
                else -> {
                    showLoading()
                    if (!isCreated) {
                        isCreated = true
                        presenter.modifyReview(
                            uriSet.map { it.toString().replace("file://", "") },
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
            maxCheck()
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
            .min(min, R.string.min_msg)
            .max(max, R.string.max_msg)
            .errorListener { message -> Log.d("ted", "message: $message") }
            .selectedUri(uriSet.toList())
            .startMultiImage { list: List<Uri> -> showMultiImage(list) }
    }

    private fun showMultiImage(uris: List<Uri>) {

        if (uriSet.isNotEmpty()) {
            if (!uris.containsAll(uriSet)) {
                image_container.removeViews(
                    imageNumbers.count() - deleteImageNumber.count(),
                    uriSet.count() - uris.count()
                )
            }
        }

        uris.forEachIndexed { index, uri ->
            val vgImage = LayoutInflater.from(this)
                .inflate(
                    R.layout.slider_item_image,
                    image_container,
                    false
                ) as ViewGroup

            val ivImage = vgImage.findViewById<ImageView>(R.id.image_item)
            val btnDelete = vgImage.findViewById<ImageButton>(R.id.btn_delete)

            ivImage.run {
                glideImageSet(uri, measuredWidth, measuredHeight)
                if (uriSet.isNotEmpty()) {
                    if (!uriSet.contains(uri)) {
                        image_container.addView(vgImage)
                    }
                } else {
                    image_container.addView(vgImage)
                }
            }

            btnDelete.setOnClickListener {
                image_container.removeView(vgImage)
                if (uriSet.isNotEmpty()) {
                    uriSet.remove(uri)
                }
            }
        }
        if (uriSet.isNotEmpty()) {
            uriSet.clear()
        }
        uriSet.addAll(uris)
    }

    private fun maxCheck() {
        when (savedImages.size) {
            0 -> {
                max = 4
                min = 1
            }
            1 -> {
                max = 3
                min = 1
            }
            2 -> {
                max = 2
                min = 1
            }
            3 -> {
                max = 1
                min = 1
            }
            4 -> {
                max = 0
                min = 0
            }
        }
    }


    companion object {
        const val PLACE_NAME = "placeName"
        const val PLACE_NUMBER = "placeNumber"
        const val MEMBER_NUMBER = "memberNumber"
        const val REVIEW_NUMBER = "reviewNumber"
    }
}
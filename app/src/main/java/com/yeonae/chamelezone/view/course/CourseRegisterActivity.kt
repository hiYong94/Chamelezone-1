package com.yeonae.chamelezone.view.course

import android.Manifest
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.PlaceItem
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.ext.hideLoading
import com.yeonae.chamelezone.ext.shortToast
import com.yeonae.chamelezone.ext.showLoading
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.LoadingDialogFragment
import com.yeonae.chamelezone.view.course.presenter.CourseRegisterContract
import com.yeonae.chamelezone.view.course.presenter.CourseRegisterPresenter
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.android.synthetic.main.activity_course_register.*

class CourseRegisterActivity : AppCompatActivity(), CourseRegisterContract.View,
    PlaceCheckDialogFragment.OnClickListener {
    override lateinit var presenter: CourseRegisterContract.Presenter
    private var imageUri: String = ""
    var memberNumber: Int = 0
    private var firstPlaceNumber: Int = NOT_SELECTED
    private var secondPlaceNumber: Int = NOT_SELECTED
    private var thirdPlaceNumber: Int = NOT_SELECTED
    private val placeNumbers = mutableListOf<Int>()
    private var isCreated = false
    private var isClicked = false

    override fun onClick(place: PlaceItem, placeIndex: Int) {
        getVisible(place, placeIndex)
    }

    override fun deliverUserInfo(user: UserEntity) {
        memberNumber = user.userNumber ?: 0
    }

    override fun showMessage(message: String) {
        this.shortToast(message)
        hideLoading()
        finish()
    }

    private fun showSingleImage(uri: Uri) {
        imageContainer.removeAllViews()
        val ivSlideImg = LayoutInflater.from(this).inflate(
            R.layout.slider_item_image,
            imageContainer,
            false
        ) as ImageView
        imageContainer.addView(ivSlideImg)
        ivSlideImg.findViewById<ImageView>(R.id.image_item).run {
            glideImageSet(uri, measuredWidth, measuredHeight)
        }
        if (!uri.path.isNullOrEmpty()) {
            imageUri = uri.path.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_register)

        setupGUI()

        presenter = CourseRegisterPresenter(
            Injection.memberRepository(), Injection.courseRepository(), this
        )

        presenter.getUser()

        btn_back.setOnClickListener {
            finish()
        }

        btn_place_add1.setOnClickListener {
            PlaceCheckDialogFragment.newInstance(1).show(supportFragmentManager, "dialog")
        }

        btn_place_add2.setOnClickListener {
            PlaceCheckDialogFragment.newInstance(2).show(supportFragmentManager, "dialog")
        }

        btn_place_add3.setOnClickListener {
            PlaceCheckDialogFragment.newInstance(3).show(supportFragmentManager, "dialog")
        }

        btn_close1.setOnClickListener {
            firstPlaceNumber = NOT_SELECTED
            tv_place_name1.text = ""
            tv_place_keyword1.text = ""
            tv_place_address1.text = ""
            layout_course1.visibility = View.GONE
            layout_place_add1.visibility = View.VISIBLE
        }

        btn_close2.setOnClickListener {
            secondPlaceNumber = NOT_SELECTED
            tv_place_name2.text = ""
            tv_place_keyword2.text = ""
            tv_place_address2.text = ""
            layout_course2.visibility = View.GONE
            layout_place_add2.visibility = View.VISIBLE
        }

        btn_close3.setOnClickListener {
            thirdPlaceNumber = NOT_SELECTED
            tv_place_name3.text = ""
            tv_place_keyword3.text = ""
            tv_place_address3.text = ""
            layout_course3.visibility = View.GONE
            layout_place_add3.visibility = View.VISIBLE
        }

        btn_register.setOnClickListener {
            placeNumbers.clear()
            if (firstPlaceNumber != NOT_SELECTED) {
                placeNumbers.add(firstPlaceNumber)
            }
            if (secondPlaceNumber != NOT_SELECTED) {
                placeNumbers.add(secondPlaceNumber)
            }
            if (thirdPlaceNumber != NOT_SELECTED) {
                placeNumbers.add(thirdPlaceNumber)
            }
            when {
                edt_course_title.text.isEmpty() -> shortToast(R.string.enter_course_title)
                edt_course_content.text.isEmpty() -> shortToast(R.string.enter_course_content)
                tv_place_name1.text.isEmpty() -> shortToast(R.string.select_two_places)
                tv_place_name2.text.isEmpty() -> shortToast(R.string.select_two_places)
                imageUri.isEmpty() -> shortToast(R.string.enter_course_image)
            }
            showLoading()
            if (!isClicked) {
                isClicked = true
                presenter.registerCourse(
                    memberNumber,
                    placeNumbers,
                    "${edt_course_title.text}",
                    "${edt_course_content.text}",
                    imageUri
                )
                Handler().postDelayed({
                    isClicked = false
                }, 5000)
            }
        }
    }

    private val permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            val prefs: SharedPreferences =
                this@CourseRegisterActivity.getSharedPreferences("Pref", MODE_PRIVATE)
            val isFirstRun = prefs.getBoolean("isFirstRun", true)
            if (isFirstRun) {
                Toast.makeText(
                    this@CourseRegisterActivity,
                    R.string.permission_granted,
                    Toast.LENGTH_SHORT
                ).show()
                prefs.edit().putBoolean("isFirstRun", false).apply()
            }
            setNormalSingleButton()
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            isCreated = false
            Toast.makeText(
                this@CourseRegisterActivity,
                getString(R.string.permission_denied) + "\n$deniedPermissions",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun getVisible(place: PlaceItem, placeIndex: Int) {
        when (placeIndex) {
            1 -> {
                firstPlaceNumber = place.placeNumber
                tv_place_name1.text = place.name
                tv_place_keyword1.text = place.keyword
                tv_place_address1.text = place.address
                iv_place_image1.glideImageSet(
                    place.image,
                    iv_place_image1.measuredWidth,
                    iv_place_image1.measuredHeight
                )
                layout_place_add1.visibility = View.GONE
                layout_course1.visibility = View.VISIBLE
            }
            2 -> {
                if (firstPlaceNumber == place.placeNumber) {
                    shortToast(R.string.selected_place)
                } else {
                    secondPlaceNumber = place.placeNumber
                    tv_place_name2.text = place.name
                    tv_place_keyword2.text = place.keyword
                    tv_place_address2.text = place.address
                    iv_place_image2.glideImageSet(
                        place.image,
                        iv_place_image1.measuredWidth,
                        iv_place_image1.measuredHeight
                    )
                    layout_place_add2.visibility = View.GONE
                    layout_course2.visibility = View.VISIBLE
                }
            }
            3 -> {
                when {
                    firstPlaceNumber == place.placeNumber -> shortToast(R.string.selected_place)
                    secondPlaceNumber == place.placeNumber -> shortToast(R.string.selected_place)
                    else -> {
                        thirdPlaceNumber = place.placeNumber
                        tv_place_name3.text = place.name
                        tv_place_keyword3.text = place.keyword
                        tv_place_address3.text = place.address
                        iv_place_image3.glideImageSet(
                            place.image, iv_place_image1.measuredWidth,
                            iv_place_image1.measuredHeight
                        )
                        layout_place_add3.visibility = View.GONE
                        layout_course3.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setNormalSingleButton() {
        TedImagePicker.with(this)
            .start { uri -> showSingleImage(uri) }
    }

    private fun setupGUI() {
        btn_image_create.setOnClickListener {
            checkPermission()
        }
        btn_image_clear.setOnClickListener { imageContainer.removeAllViews() }
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
        private const val NOT_SELECTED = -1
    }
}
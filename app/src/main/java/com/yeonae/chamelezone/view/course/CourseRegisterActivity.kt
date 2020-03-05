package com.yeonae.chamelezone.view.course

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.kroegerama.imgpicker.ButtonType
import com.kroegerama.kaiteki.toast
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.PlaceItem
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.ext.shortToast
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.Context.APPLICATION_CONTEXT
import com.yeonae.chamelezone.view.course.presenter.CourseRegisterContract
import com.yeonae.chamelezone.view.course.presenter.CourseRegisterPresenter
import kotlinx.android.synthetic.main.activity_course_register.*
import kotlinx.android.synthetic.main.slider_item_image.view.*

class CourseRegisterActivity : AppCompatActivity(), CourseRegisterContract.View,
    BottomSheetImagePicker.OnImagesSelectedListener, PlaceCheckDialogFragment.OnClickListener {
    override fun onClick(place: PlaceItem, placeIndex: Int) {
        getVisible(place, placeIndex)
    }

    override lateinit var presenter: CourseRegisterContract.Presenter
    private var imageUri: String = ""
    var memberNumber: Int = 0
    private var firstPlaceNumber: Int = NOT_SELECTED
    private var secondPlaceNumber: Int = NOT_SELECTED
    private var thirdPlaceNumber: Int = NOT_SELECTED
    private val placeNumbers = mutableListOf<Int>()
    private var isCreated = false

    override fun deliverUserInfo(user: UserEntity) {
        memberNumber = user.userNumber ?: 0
    }

    override fun showMessage(message: String) {
        this.shortToast(message)
        finish()
    }

    override fun onImagesSelected(uris: List<Uri>, tag: String?) {
        toast("$tag")
        imageContainer.removeAllViews()
        uris.forEach { uri ->
            val rl = LayoutInflater.from(this).inflate(
                R.layout.slider_item_image,
                imageContainer,
                false
            ) as RelativeLayout
            imageContainer.addView(rl)
            rl.image_item.run {
                glideImageSet(uri, measuredWidth, measuredHeight)
            }
        }
        if (!uris[0].path.isNullOrEmpty()) {
            imageUri = uris[0].path.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_register)

        setupGUI()

        presenter = CourseRegisterPresenter(
            Injection.memberRepository(APPLICATION_CONTEXT), Injection.courseRepository(), this
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
            when {
                firstPlaceNumber != NOT_SELECTED -> placeNumbers.add(firstPlaceNumber)
                secondPlaceNumber != NOT_SELECTED -> placeNumbers.add(secondPlaceNumber)
                thirdPlaceNumber != NOT_SELECTED -> placeNumbers.add(thirdPlaceNumber)
                edt_course_title.text.isEmpty() -> shortToast(R.string.enter_course_title)
                edt_course_content.text.isEmpty() -> shortToast(R.string.enter_course_content)
                tv_place_name1.text.isEmpty() -> shortToast(R.string.enter_place)
                tv_place_name2.text.isEmpty() -> shortToast(R.string.enter_place)
                imageUri.isEmpty() -> shortToast(R.string.enter_course_image)
            }
            if (!isCreated) {
                isCreated = true
                Log.d("isCreated", isCreated.toString())
                Handler().postDelayed({
                    presenter.registerCourse(
                        memberNumber,
                        placeNumbers,
                        "${edt_course_title.text}",
                        "${edt_course_content.text}",
                        imageUri
                    )
                    isCreated = false
                }, 1000)
            }
        }
    }

    private val permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            Toast.makeText(this@CourseRegisterActivity, "권한이 허용되었습니다", Toast.LENGTH_SHORT).show()
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            Toast.makeText(
                this@CourseRegisterActivity,
                "권한이 거부되었습니다\n$deniedPermissions",
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
                    shortToast("이미 선택된 장소 입니다.")
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
                if (firstPlaceNumber == place.placeNumber) {
                    shortToast("이미 선택된 장소 입니다.")
                } else if (secondPlaceNumber == place.placeNumber) {
                    shortToast("이미 선택된 장소 입니다.")
                } else {
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

    private fun pickSingle() {
        BottomSheetImagePicker.Builder(getString(R.string.file_provider))
            .cameraButton(ButtonType.Button)
            .galleryButton(ButtonType.Button)
            .singleSelectTitle(R.string.pick_single)
            .peekHeight(R.dimen.peekHeight)
            .columnSize(R.dimen.columnSize)
            .requestTag("사진이 선택되었습니다.")
            .show(supportFragmentManager)
    }

    private fun setupGUI() {
        btn_image_create.setOnClickListener {
            checkPermission()
            pickSingle()
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
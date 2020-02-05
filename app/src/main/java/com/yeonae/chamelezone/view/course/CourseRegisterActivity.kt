package com.yeonae.chamelezone.view.course

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.kroegerama.imgpicker.ButtonType
import com.kroegerama.kaiteki.toast
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Place
import kotlinx.android.synthetic.main.activity_course_register.*
import kotlinx.android.synthetic.main.slider_item_image.*

class CourseRegisterActivity : AppCompatActivity(),
    BottomSheetImagePicker.OnImagesSelectedListener {
    override fun onImagesSelected(uris: List<Uri>, tag: String?) {
        toast("$tag")
        imageContainer.removeAllViews()
        uris.forEach { uri ->
            val iv = LayoutInflater.from(this).inflate(
                R.layout.slider_item_image,
                imageContainer,
                false
            ) as ImageView
            imageContainer.addView(iv)
            Glide.with(this)
                .load(uri)
                .override(image_item.measuredWidth, image_item.measuredHeight)
                .centerCrop()
                .into(iv)
        }
    }

    private val placeChoiceFragment = PlaceChoiceFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_register)

        setupGUI()

//        btn_image_create.setOnClickListener { checkPermission() }

        btn_back.setOnClickListener {
            finish()
        }

        btn_place1.setOnClickListener {
            replace("1")
        }

        btn_place2.setOnClickListener {
            replace("2")
        }

        btn_place3.setOnClickListener {
            replace("3")
        }

        btn_close1.setOnClickListener {
            tv_place_name1.text = ""
            tv_place_keyword1.text = ""
            tv_place_address1.text = ""
            layout_place1.visibility = View.VISIBLE
            layout_place2.visibility = View.VISIBLE
            layout_course1.visibility = View.GONE
            if (tv_place_name2.text != "") {
                layout_place2.visibility = View.GONE
            }
        }

        btn_close2.setOnClickListener {
            tv_place_name2.text = ""
            tv_place_keyword2.text = ""
            tv_place_address2.text = ""
            layout_place2.visibility = View.VISIBLE
            layout_course2.visibility = View.GONE
            if (tv_place_name1.text != "") {
                layout_place1.visibility = View.GONE
                layout_course1.visibility = View.VISIBLE
            } else {
                layout_place1.visibility = View.VISIBLE
                layout_course1.visibility = View.GONE
            }
            if (tv_place_name3.text != "") {
                layout_place3.visibility = View.GONE
                layout_course3.visibility = View.VISIBLE
            } else {
                layout_place3.visibility = View.VISIBLE
                layout_course3.visibility = View.GONE
            }
        }

        btn_close3.setOnClickListener {
            tv_place_name3.text = ""
            tv_place_keyword3.text = ""
            tv_place_address3.text = ""
            layout_place3.visibility = View.VISIBLE
            layout_course3.visibility = View.GONE
            if (tv_place_name1.text != "") {
                layout_place1.visibility = View.GONE
                layout_course1.visibility = View.VISIBLE
            } else {
                layout_place1.visibility = View.VISIBLE
                layout_course1.visibility = View.GONE
            }
            if (tv_place_name2.text != "") {
                layout_place2.visibility = View.GONE
                layout_course2.visibility = View.VISIBLE
            } else {
                layout_place2.visibility = View.VISIBLE
                layout_course2.visibility = View.GONE
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

    fun getVisible(placeIndex: String, place: Place) {
        when (placeIndex) {
            "1" -> {
                tv_place_name1.text = place.placeName
                tv_place_keyword1.text = place.placeKeyword
                tv_place_address1.text = place.placeAddress
                layout_place1.visibility = View.GONE
                layout_course1.visibility = View.VISIBLE
                layout_place2.visibility = View.GONE
                if (tv_place_name2.text == "") {
                    layout_place2.visibility = View.VISIBLE
                }

            }
            "2" -> {
                tv_place_name2.text = place.placeName
                tv_place_keyword2.text = place.placeKeyword
                tv_place_address2.text = place.placeAddress
                layout_place2.visibility = View.GONE
                layout_course2.visibility = View.VISIBLE
                layout_place1.visibility = View.GONE
                layout_course1.visibility = View.VISIBLE
                layout_place3.visibility = View.GONE
                layout_course3.visibility = View.VISIBLE
                if (tv_place_name1.text == "") {
                    layout_place1.visibility = View.VISIBLE
                    layout_course1.visibility = View.GONE
                }
                if (tv_place_name3.text == "") {
                    layout_place3.visibility = View.VISIBLE
                    layout_course3.visibility = View.GONE
                }

            }
            "3" -> {
                tv_place_name3.text = place.placeName
                tv_place_keyword3.text = place.placeKeyword
                tv_place_address3.text = place.placeAddress
                layout_place3.visibility = View.GONE
                layout_course3.visibility = View.VISIBLE
                layout_place1.visibility = View.GONE
                layout_course1.visibility = View.VISIBLE
                layout_place2.visibility = View.GONE
                layout_course2.visibility = View.VISIBLE
                if (tv_place_name1.text == "") {
                    layout_place1.visibility = View.VISIBLE
                    layout_course1.visibility = View.GONE
                }
                if (tv_place_name2.text == "") {
                    layout_place2.visibility = View.VISIBLE
                    layout_course2.visibility = View.GONE
                }
            }
        }
    }

    fun replace(placeIndex: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_place_choice, placeChoiceFragment.newInstance(placeIndex))
            .addToBackStack(null)
            .commit()
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

}
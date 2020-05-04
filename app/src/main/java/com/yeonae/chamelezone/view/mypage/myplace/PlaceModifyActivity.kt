package com.yeonae.chamelezone.view.mypage.myplace

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.maps.model.LatLng
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.*
import com.yeonae.chamelezone.ext.Url.IMAGE_RESOURCE
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.view.mypage.myplace.presenter.PlaceModifyContract
import com.yeonae.chamelezone.view.mypage.myplace.presenter.PlaceModifyPresenter
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import kotlinx.android.synthetic.main.activity_place_modify.*
import java.io.IOException

class PlaceModifyActivity : AppCompatActivity(), PlaceModifyContract.View,
    KeywordModifyFragment.OnClickListener {
    override lateinit var presenter: PlaceModifyContract.Presenter
    private var openingHours = ArrayList<String>()
    private var openingHoursPosition = ArrayList<String>()
    private val keywordMap = hashMapOf<Int, String>()
    private var keywords = mutableListOf<Int>()
    private var selectedKeyword = arrayListOf<String>()
    private var isCreated = false
    private lateinit var latLng: LatLng
    lateinit var latitude: String
    lateinit var longitude: String
    private val imageNumbers = arrayListOf<Int>()
    private var deleteImageNumbers = arrayListOf<Int>()
    private var placeKeywordNumbers = arrayListOf<Int>()
    private var savedImageList = arrayListOf<String>()
    private var max = 4
    private var min = 1
    private val uriSet = mutableSetOf<Uri>()
    private lateinit var originPlace: PlaceResponse

    override fun showResult(response: Boolean) {
        if (response) {
            shortToast(R.string.success_update_place)
            hideLoading()
            val intent = Intent(this, MyPlaceActivity::class.java)
            intent.putExtra("update", true)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onClick(keywordList: ArrayList<String>) {
        keywords.clear()
        for (i in 0 until keywordList.size) {
            for (j in 0 until keywordMap.size) {
                if (keywordMap[j] == keywordList[i]) {
                    keywords.add(j)
                }
            }
        }
        selectedKeyword = keywordList
        tv_place_keyword.text = keywordList.toString().replace("[", "").replace("]", "")
    }

    private fun showMultiImage(uris: List<Uri>) {
        uris.forEachIndexed { _, uri ->
            val viewGroup = LayoutInflater.from(this)
                .inflate(
                    R.layout.slider_item_image,
                    image_container,
                    false
                ) as ViewGroup
            val ivImage = viewGroup.findViewById<ImageView>(R.id.image_item)
            val btnDelete = viewGroup.findViewById<ImageButton>(R.id.btn_delete)

            ivImage.run {
                glideImageSet(uri, measuredWidth, measuredHeight)
                if (uriSet.isNotEmpty()) {
                    if (!uriSet.contains(uri)) {
                        image_container.addView(viewGroup)
                    }
                } else {
                    image_container.addView(viewGroup)
                }
            }

            btnDelete.setOnClickListener {
                image_container.removeView(viewGroup)
                if (uriSet.isNotEmpty()) {
                    uriSet.remove(uri)
                }
            }
        }

        uriSet.addAll(uris)
    }

    override fun showPlaceDetail(place: PlaceResponse) {
        originPlace = place
        place.imageNumbers.forEach {
            imageNumbers.add(it)
        }
        image_container.removeAllViews()
        place.savedImageName.forEachIndexed { index, image ->
            savedImageList.add(image)
            val clSliderImg = LayoutInflater.from(this).inflate(
                R.layout.slider_item_image,
                image_container,
                false
            ) as ConstraintLayout
            image_container.addView(clSliderImg)
            clSliderImg.findViewById<ImageView>(R.id.image_item).run {
                glideImageSet(IMAGE_RESOURCE + image, measuredWidth, measuredHeight)
            }
            clSliderImg.findViewById<ImageView>(R.id.btn_delete).setOnClickListener {
                image_container.removeView(clSliderImg)
                deleteImageNumbers.add(imageNumbers[index])
                savedImageList.remove(image)
            }
        }
        edt_place_name.text = SpannableStringBuilder(place.name)
        tv_place_address.text = place.address
        place.keywordName.forEach {
            if (it == place.keywordName[0]) {
                tv_place_keyword.text = it
            } else {
                tv_place_keyword.text = "${tv_place_keyword.text}${","} $it"
            }
        }
        tv_opening_time.visibility = View.VISIBLE
        place.openingTime.forEach {
            if (it == place.openingTime[0]) {
                tv_opening_time.text = it
            } else {
                tv_opening_time.text = "${tv_opening_time.text}\n$it"
            }
        }
        edt_place_phone.text = SpannableStringBuilder(place.phoneNumber)
        edt_place_text.text = SpannableStringBuilder(place.content)
        if (place.addressDetail != null) {
            edt_detail_address.text = SpannableStringBuilder(place.addressDetail)
        }

        placeKeywordNumbers = place.placeKeywordNumbers
        selectedKeyword = place.keywordName
        openingHoursPosition = place.openingTime
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_modify)

        setupGUI()

        presenter = PlaceModifyPresenter(
            Injection.placeRepository(), this
        )

        val placeNumber = intent.getIntExtra("placeNumber", 0)
        val memberNumber = intent.getIntExtra("memberNumber", 0)

        presenter.getPlaceDetail(placeNumber, memberNumber)

        edt_place_text.setTouchForScrollBars()
        edt_place_phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        btn_back.setOnClickListener {
            finish()
        }

        btn_image_clear.setOnClickListener {
            savedImageList.clear()
            maxCheck()
            uriSet.clear()
            image_container.removeAllViews()
            deleteImageNumbers = imageNumbers
        }

        btn_opening_hour.setOnClickListener {
            val intent = Intent(this, OpeningHoursModifyActivity::class.java)
            intent.putExtra("selectedPosition", openingHoursPosition)
            intent.putExtra("placeNumber", placeNumber)
            startActivityForResult(intent, OPENING_HOURS_REQUEST_CODE)
        }

        btn_address_search.setOnClickListener {
            val intent = Intent(this, SearchAddressActivity::class.java)
            startActivityForResult(intent, ADDRESS_REQUEST_CODE)
        }

        btn_category_choice.setOnClickListener {
            val newFragment =
                KeywordModifyFragment.newInstance(
                    placeNumber,
                    placeKeywordNumbers,
                    selectedKeyword
                )
            newFragment.show(supportFragmentManager, "dialog")
        }

        btn_modify.setOnClickListener {
            when {
                "${edt_place_name.text}".trim().isEmpty() -> shortToast(R.string.enter_place_name)
                tv_place_keyword.text.isEmpty() -> shortToast(R.string.enter_place_keyword)
                tv_place_address.text.isEmpty() -> shortToast(R.string.enter_place_address)
                tv_opening_time.text.isEmpty() -> shortToast(R.string.enter_place_opening_hours)
                "${edt_place_phone.text}".trim().isEmpty() -> shortToast(R.string.enter_place_phone)
                "${edt_place_text.text}".trim()
                    .isEmpty() -> shortToast(R.string.enter_place_content)
                savedImageList.isEmpty() && uriSet.isEmpty() -> shortToast(R.string.enter_place_image)
                selectedKeyword.size == 1 -> shortToast(R.string.keyword_select)
                else -> {
                    latLng = findLatLng(applicationContext, "${tv_place_address.text}")
                    latitude = latLng.latitude.toString()
                    longitude = latLng.longitude.toString()
                    if ("${tv_place_address.text}" == originPlace.address &&
                        "${edt_detail_address.text}" == originPlace.addressDetail &&
                        latitude == originPlace.latitude &&
                        longitude == originPlace.longitude &&
                        "${edt_place_phone.text}" == originPlace.phoneNumber &&
                        "${edt_place_text.text}" == originPlace.content
                    ) {
                        finish()
                    } else {
                        updatePlace(placeNumber, memberNumber)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADDRESS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                tv_place_address.text = data?.getStringExtra(RESULT)
            }
        } else if (requestCode == OPENING_HOURS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                openingHours = data?.getStringArrayListExtra(OPENING_HOURS) as ArrayList<String>
                openingHoursPosition =
                    data.getStringArrayListExtra(OPENING_HOURS_POSITION) as ArrayList<String>
                openingHours.forEach {
                    if (it == openingHours[0]) {
                        tv_opening_time.text = it
                    } else {
                        tv_opening_time.text = "${tv_opening_time.text}\n$it"
                    }
                }
                tv_opening_time.visibility = View.VISIBLE
            }
        }
    }

    private fun updatePlace(placeNumber: Int, memberNumber: Int) {
        showLoading()
        if (uriSet.isEmpty()) {
            presenter.updatePlace(
                placeNumber,
                memberNumber,
                "${tv_place_address.text}",
                "${edt_detail_address.text}",
                "${edt_place_phone.text}",
                "${edt_place_text.text}",
                latitude.toBigDecimal(),
                longitude.toBigDecimal()
            )
        } else {
            presenter.updatePlace(
                placeNumber,
                uriSet.map { it.toString().replace("file://", "") },
                deleteImageNumbers,
                memberNumber,
                "${tv_place_address.text}",
                "${edt_detail_address.text}",
                "${edt_place_phone.text}",
                "${edt_place_text.text}",
                latitude.toBigDecimal(),
                longitude.toBigDecimal()
            )
        }
    }

    private fun findLatLng(context: Context, address: String): LatLng {
        val coder = Geocoder(context)
        var addresses: List<Address>? = null
        lateinit var latLng: LatLng

        try {
            addresses = coder.getFromLocationName(address, 5)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (addresses != null) {
            for (i in addresses.indices) {
                latLng = LatLng(addresses[i].latitude, addresses[i].longitude)
            }
        }
        return latLng
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

    private fun setupGUI() {
        btn_image_create.setOnClickListener {
            maxCheck()
            if (!isCreated) {
                isCreated = true
                checkPermission()
            }
            Handler().postDelayed({
                isCreated = false
            }, 1000)
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
                this@PlaceModifyActivity.getSharedPreferences("Pref", MODE_PRIVATE)
            val isFirstRun = prefs.getBoolean("isFirstRun", true)
            if (isFirstRun) {
                Toast.makeText(
                    this@PlaceModifyActivity,
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
                this@PlaceModifyActivity,
                getString(R.string.permission_denied) + "\n$deniedPermissions",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun maxCheck() {
        when (savedImageList.size) {
            0 -> {
                max = 4
                min = 2
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
        const val ADDRESS_REQUEST_CODE = 1
        const val OPENING_HOURS_REQUEST_CODE = 2
        const val RESULT = "result"
        const val OPENING_HOURS = "openingHours"
        const val OPENING_HOURS_POSITION = "openingHoursPosition"
    }
}
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
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    var memberNumber: Int = 0
    private var imageUri = arrayListOf<String>()
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
    private var uriDataList = arrayListOf<String>()
    private var selectedUriList = mutableListOf<Uri>()
    private var savedImageList = arrayListOf<String>()

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
        if (uriDataList.count() != 0)
            uriDataList.clear()
        this.selectedUriList = uris.toMutableList()
        uris.forEach{ uri ->
            val rlSlideImg = LayoutInflater.from(this).inflate(
                R.layout.slider_item_image,
                imageContainer,
                false
            ) as RelativeLayout
            imageContainer.addView(rlSlideImg)
            rlSlideImg.findViewById<ImageView>(R.id.image_item).run {
                glideImageSet(uri, measuredWidth, measuredHeight)
            }
            rlSlideImg.findViewById<ImageView>(R.id.btn_delete).setOnClickListener {
                imageContainer.removeView(rlSlideImg)
                if (this.selectedUriList.count() != 0)
                    this.selectedUriList.remove(uri)
            }

            btn_image_clear.setOnClickListener {
                imageContainer.removeAllViews()
                deleteImageNumbers = imageNumbers
                if (this.selectedUriList.count() != 0)
                    this.selectedUriList.removeAll(uris)
            }

            uri.path?.let { uriDataList.add(it) }
            val distinctData = uriDataList.distinct()
            imageUri = ArrayList(distinctData)
        }
    }

    override fun showPlaceDetail(place: PlaceResponse) {
        place.imageNumbers.forEach {
            imageNumbers.add(it)
        }
        imageContainer.removeAllViews()
        place.savedImageName.forEachIndexed { index, image ->
            savedImageList.add(image)
            val rlSlideImg = LayoutInflater.from(this).inflate(
                R.layout.slider_item_image,
                imageContainer,
                false
            ) as RelativeLayout
            imageContainer.addView(rlSlideImg)
            rlSlideImg.findViewById<ImageView>(R.id.image_item).run {
                glideImageSet(IMAGE_RESOURCE + image, measuredWidth, measuredHeight)
            }
            rlSlideImg.findViewById<ImageView>(R.id.btn_delete).setOnClickListener {
                imageContainer.removeView(rlSlideImg)
                deleteImageNumbers.add(imageNumbers[index])
                savedImageList.removeAt(index)
            }

            btn_image_clear.setOnClickListener {
                if (deleteImageNumbers.count() != 0)
                    deleteImageNumbers.clear()
                if (savedImageList.count() != 0)
                    savedImageList.clear()
                imageContainer.removeAllViews()
                deleteImageNumbers = imageNumbers
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

        edt_place_phone.inputType = android.text.InputType.TYPE_CLASS_PHONE
        edt_place_phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        btn_back.setOnClickListener {
            finish()
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
                "${edt_place_text.text}".trim().isEmpty() -> shortToast(R.string.enter_place_content)
                savedImageList.isEmpty() && imageUri.isEmpty() -> shortToast(R.string.enter_place_image)
                selectedKeyword.size == 1 -> shortToast(R.string.keyword_select)
                else -> {
                    latLng = findLatLng(applicationContext, "${tv_place_address.text}")
                    latitude = latLng.latitude.toString()
                    longitude = latLng.longitude.toString()
                    showLoading()
                    if (uriDataList.isEmpty()) {
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
                            imageUri,
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
            .min(2, R.string.min_msg)
            .max(4, R.string.max_msg)
            .errorListener { message -> Log.d("ted", "message: $message") }
            .selectedUri(selectedUriList)
            .startMultiImage { list: List<Uri> -> showMultiImage(list) }

    }

    private fun setupGUI() {
        btn_image_create.setOnClickListener {
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

    companion object {
        const val ADDRESS_REQUEST_CODE = 1
        const val OPENING_HOURS_REQUEST_CODE = 2
        const val RESULT = "result"
        const val OPENING_HOURS = "openingHours"
        const val OPENING_HOURS_POSITION = "openingHoursPosition"
    }
}
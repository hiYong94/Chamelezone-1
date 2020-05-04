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
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.mypage.myplace.presenter.PlaceContract
import com.yeonae.chamelezone.view.mypage.myplace.presenter.PlacePresenter
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import kotlinx.android.synthetic.main.activity_place_register.*
import java.io.IOException

class PlaceRegisterActivity : AppCompatActivity(), PlaceContract.View,
    CheckDialogFragment.OnClickListener {
    override lateinit var presenter: PlaceContract.Presenter
    var memberNumber: Int = 0
    private var imageUri = arrayListOf<String>()
    private var openingHours = ArrayList<String>()
    private var openingHoursPosition = ArrayList<String>()
    private val keywordMap = hashMapOf<Int, String>()
    private var keywords = mutableListOf<Int>()
    private var selectedKeyword = arrayListOf<String>()
    private var isCreated = false
    val keywordName = arrayListOf<String>()
    private lateinit var latLng: LatLng
    lateinit var latitude: String
    lateinit var longitude: String
    private var uriDataList = arrayListOf<String>()
    private var selectedUriList = mutableListOf<Uri>()
    private var isClicked = false

    override fun showPlaceMessage(placeCheck: String) {
        if (placeCheck == CHECK_YES) {
            layout_check_place.visibility = View.GONE
            layout_add_view.visibility = View.VISIBLE
            tv_check_place.visibility = View.GONE
            edt_place_name.isEnabled = false
            tv_place_address.isEnabled = false
            edt_detail_address.isEnabled = false
            btn_address_search.isEnabled = false
            btn_register.visibility = View.VISIBLE
        } else if (placeCheck == CHECK_NO) {
            layout_check_place.visibility = View.VISIBLE
            layout_add_view.visibility = View.GONE
            tv_check_place.visibility = View.VISIBLE
            shortToast(R.string.registered_place)
            edt_place_name.isEnabled = true
            tv_place_address.isEnabled = true
            edt_detail_address.isEnabled = true
            btn_address_search.isEnabled = true
            btn_register.visibility = View.GONE
        }
    }

    override fun onClick(keywordList: ArrayList<String>) {
        keywords.clear()
        for (i in 0 until keywordList.size) {
            for (j in 1..keywordMap.size) {
                if (keywordMap[j] == keywordList[i]) {
                    keywords.add(j)
                }
            }
        }
        selectedKeyword = keywordList
        tv_place_keyword.text = keywordList.toString().replace("[", "").replace("]", "")
    }

    override fun showUserInfo(user: UserEntity) {
        memberNumber = user.userNumber ?: 0
    }

    override fun showKeywordList(response: List<KeywordResponse>) {
        for (i in response.indices) {
            keywordName.add(response[i].keywordName)
            keywordMap[response[i].keywordNumber] = response[i].keywordName
        }
    }

    private fun showMultiImage(uris: List<Uri>) {
        if (uriDataList.count() != 0)
            uriDataList.clear()
        this.selectedUriList = uris.toMutableList()
        imageContainer.removeAllViews()
        uris.forEach { uri ->
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
                if (this.selectedUriList.count() != 0)
                    this.selectedUriList.removeAll(uris)
            }
            uri.path?.let { uriDataList.add(it) }
            val distinctData = uriDataList.distinct()
            imageUri = ArrayList(distinctData)
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
            .show()
        hideLoading()
        val intent = Intent(this, MyPlaceActivity::class.java)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_register)

        setupGUI()

        presenter = PlacePresenter(
            Injection.memberRepository(), Injection.placeRepository(), this
        )

        edt_place_text.setTouchForScrollBars()

        btn_place_check.setOnClickListener {
            when {
                edt_place_name.text.isEmpty() -> shortToast(R.string.enter_place_name)
                tv_place_address.text.isEmpty() -> shortToast(R.string.enter_place_address)
                else -> {
                    latLng = findLatLng(applicationContext, "${tv_place_address.text}")
                    latitude = latLng.latitude.toString()
                    longitude = latLng.longitude.toString()
                    presenter.checkPlace(
                        "${edt_place_name.text}",
                        latitude,
                        longitude
                    )
                }
            }
        }

        presenter.getUser()
        presenter.getKeyword()

        edt_place_phone.inputType = android.text.InputType.TYPE_CLASS_PHONE
        edt_place_phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        btn_back.setOnClickListener {
            finish()
        }

        btn_opening_hour.setOnClickListener {
            val intent = Intent(this, OpeningHoursActivity::class.java)
            intent.putExtra("selectedPosition", openingHoursPosition)
            startActivityForResult(intent, OPENING_HOURS_REQUEST_CODE)
        }

        btn_address_search.setOnClickListener {
            val intent = Intent(this, SearchAddressActivity::class.java)
            startActivityForResult(intent, ADDRESS_REQUEST_CODE)
        }

        btn_category_choice.setOnClickListener {
            val newFragment = CheckDialogFragment.newInstance(keywordName, selectedKeyword)
            newFragment.show(supportFragmentManager, "dialog")
        }

        btn_register.setOnClickListener {
            when {
                "${edt_place_name.text}".trim().isEmpty() -> shortToast(R.string.enter_place_name)
                tv_place_keyword.text.isEmpty() -> shortToast(R.string.enter_place_keyword)
                tv_place_address.text.isEmpty() -> shortToast(R.string.enter_place_address)
                tv_opening_time.text.isEmpty() -> shortToast(R.string.enter_place_opening_hours)
                "${edt_place_phone.text}".trim().isEmpty() -> shortToast(R.string.enter_place_phone)
                "${edt_place_text.text}".trim().isEmpty() -> shortToast(R.string.enter_place_content)
                imageUri.isEmpty() -> shortToast(R.string.enter_place_image)
                selectedKeyword.size == 1 -> shortToast(R.string.keyword_select)
                else -> {
                    Log.d("imageUri", imageUri.toString())
                    showLoading()
                    if (!isClicked) {
                        isClicked = true
                        presenter.placeRegister(
                            memberNumber,
                            keywords,
                            "${edt_place_name.text}",
                            "${tv_place_address.text}",
                            "${edt_detail_address.text}",
                            openingHours,
                            "${edt_place_phone.text}",
                            "${edt_place_text.text}",
                            latitude.toBigDecimal(),
                            longitude.toBigDecimal(),
                            imageUri
                        )
                        Handler().postDelayed({
                            isClicked = false
                        }, 5000)
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
                this@PlaceRegisterActivity.getSharedPreferences("Pref", MODE_PRIVATE)
            val isFirstRun = prefs.getBoolean("isFirstRun", true)
            if (isFirstRun) {
                Toast.makeText(
                    this@PlaceRegisterActivity,
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
                this@PlaceRegisterActivity,
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
        const val CHECK_YES = "Y"
        const val CHECK_NO = "N"
    }
}
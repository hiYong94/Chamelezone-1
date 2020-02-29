package com.yeonae.chamelezone.view.mypage.myplace

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import com.google.android.gms.maps.model.LatLng
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.kroegerama.kaiteki.toast
import com.yeonae.chamelezone.CheckDialogFragment
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.mypage.myplace.presenter.PlaceContract
import com.yeonae.chamelezone.view.mypage.myplace.presenter.PlacePresenter
import kotlinx.android.synthetic.main.activity_place_register.*
import kotlinx.android.synthetic.main.slider_item_image.*
import java.io.IOException

class PlaceRegisterActivity : AppCompatActivity(), PlaceContract.View,
    BottomSheetImagePicker.OnImagesSelectedListener, CheckDialogFragment.OnClickListener {
    var memberNumber: Int = 0
    private var imageUri = arrayListOf<String>()
    private val openingTime = mutableListOf<String>()
    private val keywordMap = hashMapOf<Int, String>()
    private val keyword = mutableListOf<Int>()
    private var isCreated = false

    override fun onClick(keyword: ArrayList<String>) {

    }

    override fun showUserInfo(user: UserEntity) {
        memberNumber = user.userNumber!!
    }

    override fun showKeywordList(response: List<KeywordResponse>) {
        for (i in response.indices) {
            keywordMap[response[i].keywordNumber] = response[i].keywordName
        }
    }

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
            iv.glideImageSet(uri, image_item.measuredWidth, image_item.measuredHeight)
        }
        for (i in uris.indices) {
            uris[i].path?.let { imageUri.add(it) }
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
            .show()
        finish()
    }

    override lateinit var presenter: PlaceContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_register)

        setupGUI()

        presenter = PlacePresenter(
            Injection.memberRepository(applicationContext), Injection.placeRepository(), this
        )
        presenter.getUser()
        presenter.getKeyword()

        edt_place_phone.inputType = android.text.InputType.TYPE_CLASS_PHONE
        edt_place_phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        btn_back.setOnClickListener {
            finish()
        }

        test.setOnClickListener {
            val intent = Intent(this, OpeningHoursActivity::class.java)
            startActivity(intent)
        }

        btn_address_search.setOnClickListener {
            val intent = Intent(this, SearchAddressActivity::class.java)
            startActivityForResult(intent, 1)
        }

        val items = keywordMap.values.toTypedArray()
        val newFragment = CheckDialogFragment()

        btn_category_choice.setOnClickListener {
            newFragment.show(supportFragmentManager, "dialog")
        }

        btn_register.setOnClickListener {
            val latLng = findLatLng(applicationContext, "${tv_place_address.text}")
            val latitude = latLng?.latitude?.toBigDecimal()
            val longitude = latLng?.longitude?.toBigDecimal()
            val realAddress = "${tv_place_address.text}" + " " + "${edt_detail_address.text}"

            if (!isCreated) {
                isCreated = true
                Handler().postDelayed({
                    if (latitude != null && longitude != null) {
                        presenter.placeRegister(
                            memberNumber,
                            keyword,
                            "${edt_place_name.text}",
                            realAddress,
                            openingTime,
                            "${edt_place_phone.text}",
                            "${edt_place_text.text}",
                            latitude,
                            longitude,
                            imageUri
                        )
                    }
                    isCreated = false
                }, 1000)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                tv_place_address.text = data?.getStringExtra("result")
            }
        }
    }

    private fun findLatLng(context: Context, address: String): LatLng? {
        val coder = Geocoder(context)
        var addresses: List<Address>? = null
        var latLng: LatLng? = null

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

    private fun pickMulti() {
        BottomSheetImagePicker.Builder(getString(R.string.file_provider))
            .multiSelect(2, 4)
            .multiSelectTitles(
                R.plurals.pick_multi,
                R.plurals.pick_multi_more,
                R.string.pick_multi_limit
            )
            .peekHeight(R.dimen.peekHeight)
            .columnSize(R.dimen.columnSize)
            .requestTag("사진이 선택되었습니다.")
            .show(supportFragmentManager)
    }

    private fun setupGUI() {
        btn_image_create.setOnClickListener { pickMulti() }
        btn_image_clear.setOnClickListener { imageContainer.removeAllViews() }
    }

    companion object {
        const val REQUEST_CODE = 1
    }
}

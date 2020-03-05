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
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.kroegerama.kaiteki.toast
import com.yeonae.chamelezone.CheckDialogFragment
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.ext.shortToast
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.network.room.entity.UserEntity
import com.yeonae.chamelezone.view.Context.APPLICATION_CONTEXT
import com.yeonae.chamelezone.view.mypage.myplace.presenter.PlaceContract
import com.yeonae.chamelezone.view.mypage.myplace.presenter.PlacePresenter
import kotlinx.android.synthetic.main.activity_place_register.*
import kotlinx.android.synthetic.main.slider_item_image.*
import java.io.IOException

class PlaceRegisterActivity : AppCompatActivity(), PlaceContract.View,
    BottomSheetImagePicker.OnImagesSelectedListener, CheckDialogFragment.OnClickListener {
    var memberNumber: Int = 0
    private var imageUri = arrayListOf<String>()
    private var openingTime = ArrayList<String>()
    private val keywordMap = hashMapOf<Int, String>()
    private var keywords = mutableListOf<Int>()
    private var selectedKeyword = arrayListOf<String>()
    private var isCreated = false
    val keywordName = arrayListOf<String>()

    override fun onClick(keyword: ArrayList<String>) {
        keywords.clear()
        for (i in 0 until keyword.size) {
            for (j in 0 until keywordMap.size) {
                if (keywordMap[j] == keyword[i]) {
                    keywords.add(j)
                }
            }
        }
        selectedKeyword = keyword
        tv_place_keyword.text = keyword.toString().replace("[", "").replace("]", "")
    }

    override fun showUserInfo(user: UserEntity) {
        memberNumber = user.userNumber!!
    }

    override fun showKeywordList(response: List<KeywordResponse>) {
        for (i in response.indices) {
            keywordName.add(response[i].keywordName)
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
            Injection.memberRepository(APPLICATION_CONTEXT), Injection.placeRepository(), this
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
            intent.putExtra("selectedOpeningHours", openingTime)
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
            val latLng = findLatLng(applicationContext, "${tv_place_address.text}")
            val latitude = latLng?.latitude?.toBigDecimal()
            val longitude = latLng?.longitude?.toBigDecimal()
            val realAddress = "${tv_place_address.text}" + " " + "${edt_detail_address.text}"
            when {
                edt_place_name.text.isEmpty() -> shortToast(R.string.enter_place_name)
                tv_place_keyword.text.isEmpty() -> shortToast(R.string.enter_place_keyword)
                tv_place_address.text.isEmpty() -> shortToast(R.string.enter_place_address)
                tv_opening_time.text.isEmpty() -> shortToast(R.string.enter_place_opening_hours)
                edt_place_phone.text.isEmpty() -> shortToast(R.string.enter_place_phone)
                edt_place_text.text.isEmpty() -> shortToast(R.string.enter_place_content)
                imageUri.isEmpty() -> shortToast(R.string.enter_place_image)
            }
            if (!isCreated) {
                isCreated = true
                Handler().postDelayed({
                    if (latitude != null && longitude != null) {
                        presenter.placeRegister(
                            memberNumber,
                            keywords,
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
        if (requestCode == ADDRESS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                tv_place_address.text = data?.getStringExtra(RESULT)
            }
        } else if (requestCode == OPENING_HOURS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                openingTime = data?.getStringArrayListExtra(OPENING_HOURS) as ArrayList<String>
                openingTime.forEach {
                    if (it == openingTime[0]) {
                        tv_opening_time.text = it
                    } else {
                        tv_opening_time.text = "${tv_opening_time.text}\n $it"
                    }
                }
                tv_opening_time.visibility = View.VISIBLE
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
        const val ADDRESS_REQUEST_CODE = 1
        const val OPENING_HOURS_REQUEST_CODE = 2
        const val RESULT = "result"
        const val OPENING_HOURS = "openingHours"
    }
}

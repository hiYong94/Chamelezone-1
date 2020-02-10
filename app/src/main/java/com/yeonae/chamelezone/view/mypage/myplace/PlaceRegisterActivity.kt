package com.yeonae.chamelezone.view.mypage.myplace

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.kroegerama.kaiteki.toast
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.ext.glideImageSet
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.view.mypage.myplace.presenter.PlaceContract
import com.yeonae.chamelezone.view.mypage.myplace.presenter.PlacePresenter
import kotlinx.android.synthetic.main.activity_place_register.*
import kotlinx.android.synthetic.main.slider_item_image.*
import java.io.IOException

class PlaceRegisterActivity : AppCompatActivity(), PlaceContract.View,
    BottomSheetImagePicker.OnImagesSelectedListener {

    private val array = mutableListOf<String>()
    override fun showKeywordList(response: List<KeywordResponse>) {
        Log.d("yeon_array1", response.toString())
        for (i in response.indices) {
            array.add(response[i].keywordName)
            Log.d("yeon_array2", array.toString())
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
    }

    override fun place(message: String) {
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
            Injection.placeRepository(applicationContext), this
        )
        presenter.getKeyword()

        edt_place_phone.inputType = android.text.InputType.TYPE_CLASS_PHONE
        edt_place_phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        btn_back.setOnClickListener {
            finish()
        }

        btn_address_search.setOnClickListener {
            val intent = Intent(this, SearchAddressActivity::class.java)
            startActivityForResult(intent, 1)
        }

        btn_category_choice.setOnClickListener {
            categoryDialog(array.toTypedArray())
        }

        btn_register.setOnClickListener {
            val latLng = findLatLng(applicationContext, "${tv_place_address.text}")
            val latitude = latLng?.latitude.toString()
            val longitude = latLng?.longitude.toString()
            val realAddress = "${tv_place_address.text}" + " " + "${edt_detail_address.text}"
            val keyword = "${tv_place_keyword.text}".replace(" ", "|")

            presenter.placeRegister(
                keyword,
                "${edt_place_name.text}",
                realAddress,
                "평일 11:00 ~ 20:00",
                "${edt_place_phone.text}",
                "${edt_place_text.text}",
                latitude,
                longitude
            )
        }
        openingHours()
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


    private fun categoryDialog(items: Array<String>) {
        val builder = AlertDialog.Builder(this)
        val selectedItems = ArrayList<String>()
        builder.setTitle("키워드")
        builder.setMultiChoiceItems(items, null,
            DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                if (isChecked) {
                    selectedItems.add(items[which]!!)
                } else if (selectedItems.contains(items[which])) {
                    selectedItems.remove(items[which])
                }
            })
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, id ->
                tv_place_keyword.text = selectedItems.joinToString(" ")
            })
        builder.setNegativeButton("취소",
            DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

        builder.create()
        builder.show()
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

    private fun openingHours() {
        addOpeningHourLayout()

        btn_add.setOnClickListener {
            addOpeningHourLayout()
        }

    }

    private fun addOpeningHourLayout() {
        val dayArray = resources.getStringArray(R.array.day_array)
        val openArray = resources.getStringArray(R.array.open_array)
        val closeArray = resources.getStringArray(R.array.close_array)

        val dayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, dayArray)
        val openAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, openArray)
        val closeAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, closeArray)

        val layout = LayoutInflater.from(applicationContext)
            .inflate(R.layout.item_opening_hours, opening_hours, false)

        val count = opening_hours.childCount
        Log.d("count_call", count.toString())

        opening_hours.addView(layout)
        Log.d("count_call_1", count.toString())
        layout.findViewById<View>(R.id.delete_layout)
            .setOnClickListener {
                opening_hours.removeView(layout)
                Log.d("count_call", count.toString())
                if (count <= 2) {
                    btn_add.visibility = View.VISIBLE
                }
            }

        if (count > 1) {
            btn_add.visibility = View.GONE
        }

        layout.findViewById<Spinner>(R.id.day_spinner).adapter = dayAdapter
        layout.findViewById<Spinner>(R.id.open_spinner).adapter = openAdapter
        layout.findViewById<Spinner>(R.id.close_spinner).adapter = closeAdapter

    }

    companion object {
        const val REQUEST_CODE = 1
    }
}

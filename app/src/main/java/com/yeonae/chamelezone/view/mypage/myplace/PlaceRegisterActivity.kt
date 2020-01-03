package com.yeonae.chamelezone.view.mypage.myplace

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.kroegerama.kaiteki.toast
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.repository.place.PlaceRepositoryImpl
import com.yeonae.chamelezone.data.source.remote.place.PlaceRemoteDataSourceImpl
import com.yeonae.chamelezone.network.api.RetrofitConnection
import com.yeonae.chamelezone.view.mypage.myplace.presenter.PlaceContract
import com.yeonae.chamelezone.view.mypage.myplace.presenter.PlacePresenter
import kotlinx.android.synthetic.main.activity_place_register.*


class PlaceRegisterActivity : AppCompatActivity(), PlaceContract.View,
    BottomSheetImagePicker.OnImagesSelectedListener {
    private val retrofitConnection = RetrofitConnection
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
            Glide.with(this).load(uri).into(iv)
        }
    }

    override fun place(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
            .show()
    }

    override lateinit var presenter: PlaceContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_register)

        setupGUI()

        presenter = PlacePresenter(
            PlaceRepositoryImpl.getInstance(
                PlaceRemoteDataSourceImpl.getInstance(retrofitConnection)
            ), this
        )

        edt_place_phone.inputType = android.text.InputType.TYPE_CLASS_PHONE
        edt_place_phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        val dayArray = resources.getStringArray(R.array.day_array)
        val openArray = resources.getStringArray(R.array.open_array)
        val closeArray = resources.getStringArray(R.array.close_array)

        val dayAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, dayArray)
        val openAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, openArray)
        val closeAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, closeArray)

        day_spinner1.adapter = dayAdapter
        open_spinner1.adapter = openAdapter
        close_spinner1.adapter = closeAdapter

        day_spinner2.adapter = dayAdapter
        open_spinner2.adapter = openAdapter
        close_spinner2.adapter = closeAdapter

        day_spinner3.adapter = dayAdapter
        open_spinner3.adapter = openAdapter
        close_spinner3.adapter = closeAdapter

        btn_add1.setOnClickListener {
            layout_open_time2.visibility = View.VISIBLE
            btn_add1.visibility = View.GONE
            btn_add2.visibility = View.VISIBLE
        }

        btn_add2.setOnClickListener {
            layout_open_time3.visibility = View.VISIBLE
            btn_add2.visibility = View.GONE
        }


        btn_back.setOnClickListener {
            finish()
        }

        btn_address_search.setOnClickListener {
            val intent = Intent(this, SearchAddressActivity::class.java)
            startActivityForResult(intent, 1)
        }

        btn_category_choice.setOnClickListener {
            categoryDialog()
        }

        btn_register.setOnClickListener {
            val realAddress = "${tv_place_address.text}" + " " + "${edt_detail_address.text}"
            presenter.placeRegister(
                1,
                "${edt_place_name.text}",
                realAddress,
                "평일 11:00 ~ 20:00",
                "${edt_place_phone.text}",
                "${edt_place_text.text}"
            )
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

    private fun categoryDialog() {
        val builder = AlertDialog.Builder(this)
        val selectedItems = ArrayList<String>()
        val items = resources.getStringArray(R.array.keyword)

        builder.setTitle("키워드")
        builder.setMultiChoiceItems(R.array.keyword, null,
            DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                if (isChecked) {
                    selectedItems.add(items[which])
                } else if (selectedItems.contains(items[which])) {
                    selectedItems.remove(items[which])
                }
            })
        builder.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, id ->
                var str = ""
                for (i in selectedItems.indices) {
                    str = str + " " + selectedItems[i]
                }
                tv_place_keyword.text = str
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

    companion object {
        const val REQUEST_CODE = 1
    }
}

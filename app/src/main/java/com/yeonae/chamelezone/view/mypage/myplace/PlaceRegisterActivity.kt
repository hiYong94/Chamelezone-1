package com.yeonae.chamelezone.view.mypage.myplace

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.api.RetrofitConnection
import com.yeonae.chamelezone.network.model.PlaceResponse
import kotlinx.android.synthetic.main.activity_place_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlaceRegisterActivity : AppCompatActivity() {
    private val retrofitConnection = RetrofitConnection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_register)

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

        day_spinner.adapter = dayAdapter
        open_spinner.adapter = openAdapter
        close_spinner.adapter = closeAdapter

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
            placeRegister()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                tv_place_address.text = data?.getStringExtra("result")
            }
        }
    }

    private fun placeRegister() {
        val realAddress = "${tv_place_address.text}" + " " + "${edt_detail_address.text}"
        val placeResponse = PlaceResponse(
            1,
            1,
            "${edt_place_name.text}",
            realAddress,
            "평일 11:00 ~ 20:00",
            "${edt_place_phone.text}",
            "${edt_place_text.text}",
            "",
            "",
            ""
        )
        val jsonObject = JsonObject().apply {
            addProperty("keywordNumber", placeResponse.keywordNumber)
            addProperty("name", placeResponse.name)
            addProperty("address", placeResponse.address)
            addProperty("openingTime", placeResponse.openingTime)
            addProperty("phoneNumber", placeResponse.phoneNumber)
            addProperty("content", placeResponse.content)
        }

        retrofitConnection.service.placeRegister(jsonObject).enqueue(object :
            Callback<PlaceResponse> {
            override fun onResponse(
                call: Call<PlaceResponse>,
                response: Response<PlaceResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@PlaceRegisterActivity, "장소 등록 성공", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
                Log.e("tag", t.toString())
            }
        })
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
}

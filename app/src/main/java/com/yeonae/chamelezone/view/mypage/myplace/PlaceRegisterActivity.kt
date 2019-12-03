package com.yeonae.chamelezone.view.mypage.myplace

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.activity_place_register.*


class PlaceRegisterActivity : AppCompatActivity() {

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
            startActivity(intent)
        }

        btn_category_choice.setOnClickListener {
            categoryDialog()
        }
    }

    private fun categoryDialog() {
        val builder = AlertDialog.Builder(this)
        val selectedItems = ArrayList<String>()
        val items = resources.getStringArray(R.array.keyword)

        builder.setTitle("키워드")

        builder.setMultiChoiceItems(
            R.array.keyword, null,
            object : DialogInterface.OnMultiChoiceClickListener {
                override fun onClick(dialog: DialogInterface?, position: Int, isChecked: Boolean) {
                    if (isChecked) {
                        selectedItems.add(items[position])
                    } else {
                        selectedItems.removeAt(position)
                    }
                }
            })

        builder.setPositiveButton("확인",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, id: Int) {
                    var str = ""
                    for (i in selectedItems.indices) {
                        str = str + " " + selectedItems[i]
                    }
                    tv_place_keyword.text = str
                }
            })

        builder.setNegativeButton("취소",
            object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, id: Int) {
                    dialog?.cancel()
                }
            })
        builder.create()
        builder.show()
    }
}

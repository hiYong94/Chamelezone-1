package com.yeonae.chamelezone

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.kroegerama.imgpicker.BottomSheetImagePicker
import kotlinx.android.synthetic.main.activity_review_create.*

class ReviewCreateActivity : AppCompatActivity()/*, BottomSheetImagePicker.OnImagesSelectedListener */{
//    override fun onImagesSelected(uris: List<Uri>, tag: String?) {
////        toast("Result from tag: $tag")
////
////        imageContainer.removeAllViews()
////        uris.forEach { uri ->
////            val iv = LayoutInflater.from(this).inflate(
////                R.layout.activity_review_create,
////                image_view_1,
////                false
////            ) as ImageView
////            imageContainer.addView(iv)
////            Glide.with(this).load(uri).into(iv)
////        }
////    }

//    val imgViewList = intArrayOf(
//        R.id.image_view_1,
//        R.id.image_view_2,
//        R.id.image_view_3,
//        R.id.image_view_4
//    )

//    private val getGalleryImage = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_create)

//        btn_img_1.setOnClickListener {
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.setDataAndType(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*"
//            )
//            startActivityForResult(intent, getGalleryImage)
//        }
//
//        btn_img_2.setOnClickListener {
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.setDataAndType(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*"
//            )
//            startActivityForResult(intent, getGalleryImage)
//        }
//
//        btn_img_3.setOnClickListener {
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.setDataAndType(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*"
//            )
//            startActivityForResult(intent, getGalleryImage)
//        }
//
//        btn_img_4.setOnClickListener {
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.setDataAndType(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*"
//            )
//            startActivityForResult(intent, getGalleryImage)
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == getGalleryImage && resultCode == RESULT_OK && data != null && data.data != null) {
//            val selectedImageUri = data.data
//            image_view_1.setImageURI(selectedImageUri)
//        }
    }
}
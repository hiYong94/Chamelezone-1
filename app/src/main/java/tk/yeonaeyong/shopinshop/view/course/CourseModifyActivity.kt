package tk.yeonaeyong.shopinshop.view.course

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.android.synthetic.main.activity_course_modify.*
import tk.yeonaeyong.shopinshop.Injection
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.data.model.PlaceItem
import tk.yeonaeyong.shopinshop.ext.*
import tk.yeonaeyong.shopinshop.ext.Url.IMAGE_RESOURCE
import tk.yeonaeyong.shopinshop.network.model.CourseResponse
import tk.yeonaeyong.shopinshop.view.course.presenter.CourseModifyContract
import tk.yeonaeyong.shopinshop.view.course.presenter.CourseModifyPresenter
import tk.yeonaeyong.shopinshop.view.mypage.mycourse.MyCourseActivity

class CourseModifyActivity : AppCompatActivity(), CourseModifyContract.View,
    PlaceCheckDialogFragment.OnClickListener {
    override lateinit var presenter: CourseModifyContract.Presenter
    private var imageUri: String = ""
    private var firstPlaceNumber: Int = NOT_SELECTED
    private var secondPlaceNumber: Int = NOT_SELECTED
    private var thirdPlaceNumber: Int = NOT_SELECTED
    private val placeNumbers = mutableListOf<Int>()
    private var isCreated = false
    private var imageNumber: Int = 0
    private var savedImageName: String = ""
    private var originCourseList = listOf<CourseResponse>()

    override fun showMessage(response: Boolean) {
        if (response) {
            this.shortToast(R.string.success_update_course)
            hideLoading()
            val intent = Intent(this, MyCourseActivity::class.java)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun showCourseDetail(courseList: List<CourseResponse>) {
        originCourseList = courseList
        savedImageName = courseList[0].courseImage
        firstPlaceNumber = courseList[0].placeNumber
        secondPlaceNumber = courseList[1].placeNumber
        imageNumber = courseList[0].courseImageNumber
        edt_course_title.text = SpannableStringBuilder(courseList[0].title)
        edt_course_content.text = SpannableStringBuilder(courseList[0].content)

        image_container.removeAllViews()
        val viewGroup = LayoutInflater.from(this).inflate(
            R.layout.slider_course_image,
            image_container,
            false
        ) as ViewGroup
        image_container.addView(viewGroup)
        viewGroup.findViewById<ImageView>(R.id.iv_item).run {
            glideImageSet(IMAGE_RESOURCE + courseList[0].courseImage, measuredWidth, measuredHeight)
        }
        viewGroup.findViewById<ImageView>(R.id.btn_delete).setOnClickListener {
            image_container.removeView(viewGroup)
            imageUri = ""
            savedImageName = ""
        }

        iv_place_image1.glideImageSet(
            IMAGE_RESOURCE + courseList[0].placeImage,
            iv_place_image1.measuredWidth,
            iv_place_image1.measuredHeight
        )
        tv_place_name1.text = courseList[0].placeName
        courseList[0].keywordName.forEach {
            if (it == courseList[0].keywordName[0]) {
                tv_place_keyword1.text = it
            } else {
                tv_place_keyword1.text = "${tv_place_keyword1.text}, $it"
            }
        }
        tv_place_address1.text = courseList[0].address

        iv_place_image2.glideImageSet(
            IMAGE_RESOURCE + courseList[1].placeImage,
            iv_place_image2.measuredWidth,
            iv_place_image2.measuredHeight
        )
        tv_place_name2.text = courseList[1].placeName
        courseList[1].keywordName.forEach {
            if (it == courseList[1].keywordName[0]) {
                tv_place_keyword2.text = it
            } else {
                tv_place_keyword2.text = "${tv_place_keyword2.text}, $it"
            }
        }
        tv_place_address2.text = courseList[1].address

        if (courseList.size == 3) {
            thirdPlaceNumber = courseList[2].placeNumber
            layout_course3.visibility = View.VISIBLE
            iv_place_image3.glideImageSet(
                IMAGE_RESOURCE + courseList[2].placeImage,
                iv_place_image3.measuredWidth,
                iv_place_image3.measuredHeight
            )
            tv_place_name3.text = courseList[2].placeName
            courseList[2].keywordName.forEach {
                if (it == courseList[2].keywordName[0]) {
                    tv_place_keyword3.text = it
                } else {
                    tv_place_keyword3.text = "${tv_place_keyword3.text}, $it"
                }
            }
            tv_place_address3.text = courseList[2].address
        } else {
            layout_place_add3.visibility = View.VISIBLE
        }
    }

    override fun onClick(place: PlaceItem, placeIndex: Int) {
        getVisible(place, placeIndex)
    }

    private fun showSingleImage(uri: Uri) {
        savedImageName = ""
        image_container.removeAllViews()
        val viewGroup = LayoutInflater.from(this).inflate(
            R.layout.slider_course_image,
            image_container,
            false
        ) as ViewGroup
        image_container.addView(viewGroup)
        viewGroup.findViewById<ImageView>(R.id.iv_item).run {
            glideImageSet(uri, measuredWidth, measuredHeight)
        }
        viewGroup.findViewById<ImageView>(R.id.btn_delete).setOnClickListener {
            image_container.removeView(viewGroup)
            imageUri = ""
            savedImageName = ""
        }
        if (!uri.path.isNullOrEmpty()) {
            imageUri = uri.path.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_modify)

        presenter = CourseModifyPresenter(
            Injection.courseRepository(), this
        )
        val courseNumber = intent.getIntExtra("courseNumber", 0)
        val memberNumber = intent.getIntExtra("memberNumber", 0)
        presenter.getCourseDetail(courseNumber)

        edt_course_content.setTouchForScrollBars()

        btn_back.setOnClickListener {
            finish()
        }

        btn_image_create.setOnClickListener {
            if (!isCreated) {
                isCreated = true
                checkPermission()
            }
            Handler().postDelayed({
                isCreated = false
            }, 1000)
        }

        btn_image_clear.setOnClickListener {
            imageUri = ""
            savedImageName = ""
            image_container.removeAllViews()
        }

        btn_place_add1.setOnClickListener {
            PlaceCheckDialogFragment.newInstance(1).show(supportFragmentManager, "dialog")
        }

        btn_place_add2.setOnClickListener {
            PlaceCheckDialogFragment.newInstance(2).show(supportFragmentManager, "dialog")
        }

        btn_place_add3.setOnClickListener {
            PlaceCheckDialogFragment.newInstance(3).show(supportFragmentManager, "dialog")
        }

        btn_close1.setOnClickListener {
            firstPlaceNumber = NOT_SELECTED
            tv_place_name1.text = ""
            tv_place_keyword1.text = ""
            tv_place_address1.text = ""
            layout_course1.visibility = View.GONE
            layout_place_add1.visibility = View.VISIBLE
        }

        btn_close2.setOnClickListener {
            secondPlaceNumber = NOT_SELECTED
            tv_place_name2.text = ""
            tv_place_keyword2.text = ""
            tv_place_address2.text = ""
            layout_course2.visibility = View.GONE
            layout_place_add2.visibility = View.VISIBLE
        }

        btn_close3.setOnClickListener {
            thirdPlaceNumber = NOT_SELECTED
            tv_place_name3.text = ""
            tv_place_keyword3.text = ""
            tv_place_address3.text = ""
            layout_course3.visibility = View.GONE
            layout_place_add3.visibility = View.VISIBLE
        }

        btn_modify.setOnClickListener {
            placeNumbers.clear()
            if (firstPlaceNumber != NOT_SELECTED) {
                placeNumbers.add(firstPlaceNumber)
            }
            if (secondPlaceNumber != NOT_SELECTED) {
                placeNumbers.add(secondPlaceNumber)
            }
            if (thirdPlaceNumber != NOT_SELECTED) {
                placeNumbers.add(thirdPlaceNumber)
            }
            when {
                "${edt_course_title.text}".trim()
                    .isEmpty() -> shortToast(R.string.enter_course_title)
                "${edt_course_content.text}".trim()
                    .isEmpty() -> shortToast(R.string.enter_course_content)
                tv_place_name1.text.isEmpty() -> shortToast(R.string.select_two_places)
                tv_place_name2.text.isEmpty() -> shortToast(R.string.select_two_places)
                savedImageName.isEmpty() && imageUri.isEmpty() -> shortToast(R.string.enter_course_image)
                else -> {
                    if (originCourseList.size == 3) {
                        if (savedImageName == originCourseList[0].courseImage &&
                            firstPlaceNumber == originCourseList[0].placeNumber &&
                            secondPlaceNumber == originCourseList[1].placeNumber &&
                            thirdPlaceNumber == originCourseList[2].placeNumber &&
                            "${edt_course_title.text}" == originCourseList[0].title &&
                            "${edt_course_content.text}" == originCourseList[0].content
                        ) {
                            finish()
                        } else {
                            updateCourse(courseNumber, memberNumber)
                        }
                    } else {
                        if (savedImageName == originCourseList[0].courseImage &&
                            firstPlaceNumber == originCourseList[0].placeNumber &&
                            secondPlaceNumber == originCourseList[1].placeNumber &&
                            "${edt_course_title.text}" == originCourseList[0].title &&
                            "${edt_course_content.text}" == originCourseList[0].content
                        ) {
                            finish()
                        } else {
                            updateCourse(courseNumber, memberNumber)
                        }
                    }
                }
            }
        }
    }

    private val permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            val prefs: SharedPreferences =
                this@CourseModifyActivity.getSharedPreferences("Pref", MODE_PRIVATE)
            val isFirstRun = prefs.getBoolean("isFirstRun", true)
            if (isFirstRun) {
                Toast.makeText(
                    this@CourseModifyActivity,
                    R.string.permission_granted,
                    Toast.LENGTH_SHORT
                ).show()
                prefs.edit().putBoolean("isFirstRun", false).apply()
            }
            setNormalSingleButton()
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            isCreated = false
            Toast.makeText(
                this@CourseModifyActivity,
                getString(R.string.permission_denied) + "\n$deniedPermissions",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun getVisible(place: PlaceItem, placeIndex: Int) {
        when (placeIndex) {
            1 -> {
                firstPlaceNumber = place.placeNumber
                tv_place_name1.text = place.name
                tv_place_keyword1.text = place.keyword
                tv_place_address1.text = place.address
                iv_place_image1.glideImageSet(
                    place.image,
                    iv_place_image1.measuredWidth,
                    iv_place_image1.measuredHeight
                )
                layout_place_add1.visibility = View.GONE
                layout_course1.visibility = View.VISIBLE
            }
            2 -> {
                if (firstPlaceNumber == place.placeNumber) {
                    shortToast(R.string.selected_place)
                } else {
                    secondPlaceNumber = place.placeNumber
                    tv_place_name2.text = place.name
                    tv_place_keyword2.text = place.keyword
                    tv_place_address2.text = place.address
                    iv_place_image2.glideImageSet(
                        place.image,
                        iv_place_image1.measuredWidth,
                        iv_place_image1.measuredHeight
                    )
                    layout_place_add2.visibility = View.GONE
                    layout_course2.visibility = View.VISIBLE
                }
            }
            3 -> {
                when {
                    firstPlaceNumber == place.placeNumber -> shortToast(R.string.selected_place)
                    secondPlaceNumber == place.placeNumber -> shortToast(R.string.selected_place)
                    else -> {
                        thirdPlaceNumber = place.placeNumber
                        tv_place_name3.text = place.name
                        tv_place_keyword3.text = place.keyword
                        tv_place_address3.text = place.address
                        iv_place_image3.glideImageSet(
                            place.image, iv_place_image1.measuredWidth,
                            iv_place_image1.measuredHeight
                        )
                        layout_place_add3.visibility = View.GONE
                        layout_course3.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun updateCourse(courseNumber: Int, memberNumber: Int) {
        showLoading()
        if (imageUri.isEmpty() && savedImageName.isNotEmpty()) {
            presenter.modifyCourse(
                courseNumber,
                memberNumber,
                placeNumbers,
                "${edt_course_title.text}",
                "${edt_course_content.text}",
                savedImageName
            )
        } else {
            presenter.modifyCourse(
                courseNumber,
                memberNumber,
                placeNumbers,
                "${edt_course_title.text}",
                "${edt_course_content.text}",
                imageUri,
                imageNumber
            )
        }
    }

    private fun setNormalSingleButton() {
        TedImagePicker.with(this)
            .start { uri -> showSingleImage(uri) }
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

    companion object {
        private const val NOT_SELECTED = -1
    }
}
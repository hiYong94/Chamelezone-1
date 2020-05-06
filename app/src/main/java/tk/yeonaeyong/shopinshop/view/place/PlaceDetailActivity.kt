package tk.yeonaeyong.shopinshop.view.place

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_place_detail.*
import tk.yeonaeyong.shopinshop.Injection
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.data.model.LikeStatusItem
import tk.yeonaeyong.shopinshop.ext.Url.IMAGE_RESOURCE
import tk.yeonaeyong.shopinshop.ext.shortToast
import tk.yeonaeyong.shopinshop.network.model.PlaceResponse
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity
import tk.yeonaeyong.shopinshop.view.login.LoginActivity
import tk.yeonaeyong.shopinshop.view.place.adapter.ImageViewPagerAdapter
import tk.yeonaeyong.shopinshop.view.place.adapter.PlaceDetailPagerAdapter
import tk.yeonaeyong.shopinshop.view.place.presenter.PlaceDetailContract
import tk.yeonaeyong.shopinshop.view.place.presenter.PlaceDetailPresenter

class PlaceDetailActivity :
    AppCompatActivity(),
    PlaceDetailContract.View {
    override lateinit var presenter: PlaceDetailContract.Presenter
    private var memberNumber: Int? = null
    private var placeNumber: Int = 0
    private var placeName: String = ""
    private var nameBar = 0
    private var tabBar = 0
    private lateinit var imageAdapter: ImageViewPagerAdapter

    override fun showLikeMessage(response: LikeStatusItem) {
        if (response.likeStatus) {
            this.shortToast(R.string.select_like)
        }
    }

    override fun showDeleteLikeMessage(response: LikeStatusItem) {
        if (!response.likeStatus) {
            this.shortToast(R.string.delete_like)
        }
    }

    override fun placeInfo(place: PlaceResponse) {
        val images = arrayListOf<String>()
        place.savedImageName.forEach {
            images.add(IMAGE_RESOURCE + it)
        }
        if (place.likeStatus) {
            btn_like.isChecked = true
        }
        val memberNumber = memberNumber ?: 0
        imageAdapter =
            ImageViewPagerAdapter(images)
        vp_image.adapter = imageAdapter

        imageAdapter.setOnClickListener(object : ImageViewPagerAdapter.OnClickListener {
            override fun onClick(position: Int) {
                val intent = Intent(applicationContext, PlaceImageDetailActivity::class.java)
                val data = intent.apply { putExtra(POSITION, position) }
                intent.putExtra(PLACE_NUMBER, placeNumber)
                intent.putExtra(MEMBER_NUMBER, memberNumber)
                startActivity(data)
            }
        })

        if (memberNumber == null) {
            btn_like.setOnClickListener {
                btn_like.isChecked = false
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
        } else {
            btn_like.setOnClickListener {
                if (btn_like.isChecked) {
                    memberNumber.let { it1 -> presenter.selectLike(it1, placeNumber) }
                } else {
                    memberNumber.let { it1 ->
                        presenter.deleteLike(
                            it1,
                            placeNumber
                        )
                    }
                }
            }
        }
    }

    override fun deliverUserInfo(user: UserEntity) {
        memberNumber = user.userNumber
        presenter.placeDetail(placeNumber, memberNumber)
    }

    override fun showResultView(response: Boolean) {
        if (response) {
            presenter.getUser()

        } else {
            presenter.placeDetail(placeNumber, memberNumber)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)

        placeName = intent.getStringExtra(PLACE_NAME)
        placeNumber = intent.getIntExtra(PLACE_NUMBER, 0)
        tv_place_name.text = placeName
        tv_place_name_two.text = placeName

        presenter = PlaceDetailPresenter(
            Injection.memberRepository(),
            Injection.placeRepository(),
            Injection.likeRepository(),
            this
        )
        presenter.checkLogin()

        btn_back.setColorFilter(getColor(R.color.colorOrange), PorterDuff.Mode.SRC_IN)

        btn_back.setOnClickListener {
            finish()
        }

        vp_image.post {
            vp_image.layoutParams = vp_image.layoutParams.apply {
                height = ((vp_image.parent as ViewGroup).width / 3) * 2
            }
            setupView()
        }
    }

    private fun setupView() {
        tab_layout.setupWithViewPager(vp_image, true)

        val fragmentAdapter =
            PlaceDetailPagerAdapter(supportFragmentManager, placeNumber, placeName, memberNumber)
        viewpager_detail.adapter = fragmentAdapter
        tabs_detail.setupWithViewPager(viewpager_detail)

        tool_bar.run {
            post {
                nameBar = ll_title.height
                tabBar = tabs_detail.height

                layoutParams = tool_bar.layoutParams.apply {
                    height = nameBar + tabBar
                }
            }
        }
    }

    companion object {
        private const val PLACE_NAME = "placeName"
        const val PLACE_NUMBER = "placeNumber"
        const val MEMBER_NUMBER = "memberNumber"
        const val POSITION = "position"
    }
}
package com.yeonae.chamelezone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {
    val drawableList = intArrayOf(
        R.drawable.home,
        R.drawable.course,
        R.drawable.map,
        R.drawable.like,
        R.drawable.user
    )
//    val selectDrawableList = intArrayOf(
//        R.drawable.home_orange,
//        R.drawable.course_orange,
//        R.drawable.map_orange,
//        R.drawable.like_orange,
//        R.drawable.user_orange
//    )
    private val tabList by lazy { listOf("홈", "코스", "지도", "즐겨찾기", "마이페이지") }

    //액션버튼 메뉴 액션바에 집어 넣기
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_search -> {
                //검색 버튼 눌렀을 때
                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabPagerAdapter = PagerAdapter(supportFragmentManager, tabList)

        with(viewPager) {
            adapter = tabPagerAdapter
            offscreenPageLimit = 5

            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                    tabLayout.run {
                        drawableList.forEachIndexed { index, drawableId ->
                            getTabAt(index)?.setIcon(drawableId)
                        }
                        setSelectedTabIcon(position)
                    }

                }
            })
        }

        val tabView = viewPager.let {
            it.adapter = tabPagerAdapter

            it.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                    tabLayout.run {
                        drawableList.forEachIndexed { index, drawableId ->
                            getTabAt(index)?.setIcon(drawableId)
                        }
                        setSelectedTabIcon(position)
                    }
                }
            })
        }

        tabLayout.run {
            setupWithViewPager(viewPager)


            drawableList.forEachIndexed { index, drawableId ->
                getTabAt(index)?.setIcon(drawableId)
            }
            setSelectedTabIcon(0)
        }
    }

    private fun setSelectedTabIcon(position: Int) {
        when (position) {
            0 -> tabLayout.getTabAt(0)?.setIcon(R.drawable.home_orange)
            1 -> tabLayout.getTabAt(1)?.setIcon(R.drawable.course_orange)
            2 -> tabLayout.getTabAt(2)?.setIcon(R.drawable.map_orange)
            3 -> tabLayout.getTabAt(3)?.setIcon(R.drawable.like_orange)
            4 -> tabLayout.getTabAt(4)?.setIcon(R.drawable.user_orange)
        }
//        selectDrawableList.forEachIndexed { index, selectDrawable ->
//            getTabAt(index)?.setIcon(selectDrawable)

    }
}



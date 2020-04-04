package com.yeonae.chamelezone.view.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.adapter.PagerAdapter
import com.yeonae.chamelezone.view.course.CourseTabFragment
import com.yeonae.chamelezone.view.like.LikeTabFragment
import com.yeonae.chamelezone.view.map.MapTabFragment
import com.yeonae.chamelezone.view.mypage.MypageTabFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private val tabList by lazy { listOf("홈", "코스", "지도", "즐겨찾기", "MY") }
    private var time: Long = 0

    private val tabPagerAdapter = object : PagerAdapter(supportFragmentManager, tabList) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    HomeTabFragment()
                }
                1 -> {
                    CourseTabFragment()
                }
                2 -> {
                    MapTabFragment()
                }
                3 -> {
                    LikeTabFragment()
                }
                else -> MypageTabFragment()
            }
        }
    }

    val drawableList = intArrayOf(
        R.drawable.home,
        R.drawable.course,
        R.drawable.map,
        R.drawable.like,
        R.drawable.user
    )

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_search -> {
                super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupView()
    }

    private fun setupView() {
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

        viewPager.let {
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
    }

    fun replace(fragment: Fragment, isBackStack: Boolean = true) {
        if (isBackStack) {
            supportFragmentManager.beginTransaction().replace(R.id.map_fragment, fragment)
                .addToBackStack(null).commit()
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.map_fragment, fragment).commit()
        }
    }

    fun back() {
        supportFragmentManager.popBackStack()
    }

    fun tabVisible() {
        tabLayout.visibility = View.VISIBLE
    }

    fun tabGone() {
        tabLayout.visibility = View.GONE
    }

    override fun onBackPressed() {

        if (System.currentTimeMillis() > time + 2000) {
            time = System.currentTimeMillis()
            Toast.makeText(applicationContext, "뒤로 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if (System.currentTimeMillis() <= time + 2000) {
            finish()
        }
    }
}
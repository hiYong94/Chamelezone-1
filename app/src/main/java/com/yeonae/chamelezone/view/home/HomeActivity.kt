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
    var tabList = arrayOf<String>()
    private var time: Long = 0

    val drawableList = intArrayOf(
        R.drawable.ic_home_black_24dp,
        R.drawable.ic_distance_black,
        R.drawable.ic_place_black_24dp,
        R.drawable.ic_favorite_black_24dp,
        R.drawable.ic_person_black_24dp
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
        tabList = resources.getStringArray(R.array.home_tab)
        setupView()
    }

    private fun setupView() {
        val tabPagerAdapter = object : PagerAdapter(supportFragmentManager, tabList) {
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
            0 -> tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_home_orange_24dp)
            1 -> tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_distance_orange)
            2 -> tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_place_orange_24dp)
            3 -> tabLayout.getTabAt(3)?.setIcon(R.drawable.ic_favorite_orange_24dp)
            4 -> tabLayout.getTabAt(4)?.setIcon(R.drawable.ic_person_orange_24dp)
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
            Toast.makeText(applicationContext, R.string.back_pressed_msg, Toast.LENGTH_SHORT).show()
            return
        }
        if (System.currentTimeMillis() <= time + 2000) {
            finish()
        }
    }
}
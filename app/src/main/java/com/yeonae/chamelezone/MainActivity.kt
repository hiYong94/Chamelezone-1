package com.yeonae.chamelezone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //액션버튼 메뉴 액션바에 집어 넣기
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                //검색 버튼 눌렀을 때
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabPagerAdapter = PagerAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = tabPagerAdapter

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

                tabLayout.getTabAt(0)?.setIcon(R.drawable.home)
                tabLayout.getTabAt(1)?.setIcon(R.drawable.course)
                tabLayout.getTabAt(2)?.setIcon(R.drawable.map)
                tabLayout.getTabAt(3)?.setIcon(R.drawable.like)
                tabLayout.getTabAt(4)?.setIcon(R.drawable.user)

                when (position) {
                    0 -> tabLayout.getTabAt(0)?.setIcon(R.drawable.home_orange)
                    1 -> tabLayout.getTabAt(1)?.setIcon(R.drawable.course_orange)
                    2 -> tabLayout.getTabAt(2)?.setIcon(R.drawable.map_orange)
                    3 -> tabLayout.getTabAt(3)?.setIcon(R.drawable.like_orange)
                    4 -> tabLayout.getTabAt(4)?.setIcon(R.drawable.user_orange)
                }
            }
        })
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(0)?.setIcon(R.drawable.home_orange)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.course)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.map)
        tabLayout.getTabAt(3)?.setIcon(R.drawable.like)
        tabLayout.getTabAt(4)?.setIcon(R.drawable.user)
    }
}



package com.yeonae.chamelezone

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class PagerAdapter(
    fm: FragmentManager,
    private val tabCount: Int
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        when (position) {
            0 -> {
                return HomeTabFragment()
            }
            1 -> {
                return CourseTabFragment()
            }
            2 -> {
                return MapTabFragment()
            }
            3 -> {
                return LikeTabFragment()
            }
            else -> return MypageTabFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "홈"
            1 -> "코스"
            2 -> "지도"
            3 -> "즐겨찾기"
            else -> {
                return "마이페이지"

            }
        }
    }

    override fun getCount(): Int {
        return tabCount
    }

}
package com.yeonae.chamelezone

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class PagerAdapter(
    fm: FragmentManager,
    private val tabList: List<String>
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

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

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "홈"
            1 -> "코스"
            2 -> "지도"
            3 -> "즐겨찾기"
            else -> "마이페이지"
        }
    }

    override fun getCount(): Int =
        tabList.size
}
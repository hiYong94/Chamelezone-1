package com.yeonae.chamelezone.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

abstract class PagerAdapter(
    fm: FragmentManager,
    private val tabList: List<String>
    ) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    abstract override fun getItem(position: Int): Fragment

    override fun getCount(): Int =
        tabList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "홈"
            1 -> "코스"
            2 -> "지도"
            3 -> "즐겨찾기"
            else -> "마이페이지"
        }
    }
}

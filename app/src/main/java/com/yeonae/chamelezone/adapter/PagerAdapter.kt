package com.yeonae.chamelezone.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

abstract class PagerAdapter(
    fm: FragmentManager,
    private val tabList: Array<String>
    ) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    abstract override fun getItem(position: Int): Fragment

    override fun getCount(): Int =
        tabList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return tabList[position]
    }
}

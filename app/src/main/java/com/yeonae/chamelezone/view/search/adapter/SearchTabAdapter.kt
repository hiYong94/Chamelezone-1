package com.yeonae.chamelezone.view.search.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.yeonae.chamelezone.view.search.AddressTabFragment
import com.yeonae.chamelezone.view.search.KeywordTabFragment
import com.yeonae.chamelezone.view.search.PlaceNameTabFragment

class SearchTabAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PlaceNameTabFragment.newInstance()
            1 -> AddressTabFragment.newInstance()
            else -> KeywordTabFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 3
    }
}
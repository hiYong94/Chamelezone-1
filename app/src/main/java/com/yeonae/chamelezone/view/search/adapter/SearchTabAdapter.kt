package com.yeonae.chamelezone.view.search.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yeonae.chamelezone.view.search.AddressTabFragment
import com.yeonae.chamelezone.view.search.KeywordTabFragment
import com.yeonae.chamelezone.view.search.PlaceNameTabFragment

class SearchTabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PlaceNameTabFragment()
            1 -> AddressTabFragment()
            else -> KeywordTabFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

}
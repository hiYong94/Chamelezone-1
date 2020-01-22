package com.yeonae.chamelezone.view.search.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yeonae.chamelezone.view.search.AddressTabFragment
import com.yeonae.chamelezone.view.search.KeywordTabFragment
import com.yeonae.chamelezone.view.search.PlaceNameTabFragment

class SearchTabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val placeNameTabFragment = PlaceNameTabFragment()
    private val addressTabFragment = AddressTabFragment()
    private val keywordTabFragment = KeywordTabFragment()

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> placeNameTabFragment
            1 -> addressTabFragment
            else -> keywordTabFragment
        }
    }

    override fun getCount(): Int {
        return 3
    }

}
package com.yeonae.chamelezone.view.place.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.yeonae.chamelezone.view.place.PlaceInfoTabFragment
import com.yeonae.chamelezone.view.place.PlaceReviewTabFragment

class PlaceDetailPagerAdapter(
    fm: FragmentManager,  val placeNumber: Int
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {PlaceInfoTabFragment.newInstance(placeNumber)}
            else -> {PlaceReviewTabFragment()}
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "정보"
            else -> "리뷰"
        }
    }
}
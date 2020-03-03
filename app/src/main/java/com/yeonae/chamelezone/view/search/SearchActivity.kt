package com.yeonae.chamelezone.view.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.adapter.PagerAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), KeywordTabFragment.OnKeywordSelectedListener {
    override fun keywordSelected(keyword: String) {
        edt_search.setText(keyword)
        edt_search.setSelection(edt_search.length())
    }

    private val tabList by lazy { listOf("장소명", "지역명", "키워드명") }

    private val tabPagerAdapter = object : PagerAdapter(supportFragmentManager, tabList) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> PlaceNameTabFragment.newInstance()
                1 -> AddressTabFragment.newInstance()
                else -> KeywordTabFragment.newInstance()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        search_tab.setupWithViewPager(search_view_pager)
        search_view_pager.adapter = tabPagerAdapter
        search_view_pager.offscreenPageLimit = 2
        search_view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(search_tab))

        edt_search.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_GO) {
                supportFragmentManager.fragments.forEach {
                    when (it) {
                        is PlaceNameTabFragment -> it.searchByName("${edt_search.text}")
                        is AddressTabFragment -> it.searchByAddress("${edt_search.text}")
                        is KeywordTabFragment -> it.searchByKeyword("${edt_search.text}")
                    }
                }
            }
            true
        }

        btn_back.setOnClickListener {
            finish()
        }

        btn_search.setOnClickListener {
            supportFragmentManager.fragments.forEach {
                when (it) {
                    is PlaceNameTabFragment -> it.searchByName("${edt_search.text}")
                    is AddressTabFragment -> it.searchByAddress("${edt_search.text}")
                    is KeywordTabFragment -> it.searchByKeyword("${edt_search.text}")
                }
            }
        }
    }
}
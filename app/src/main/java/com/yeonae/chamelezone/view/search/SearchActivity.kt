package com.yeonae.chamelezone.view.search

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.adapter.PagerAdapter
import com.yeonae.chamelezone.ext.shortToast
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), KeywordTabFragment.OnKeywordSelectedListener {
    override fun keywordSelected(keyword: String) {
        edt_search.setText(keyword)
        edt_search.setSelection(edt_search.length())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val tabList = resources.getStringArray(R.array.search_tab)

        val tabPagerAdapter = object : PagerAdapter(supportFragmentManager, tabList) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> PlaceNameTabFragment.newInstance()
                    1 -> AddressTabFragment.newInstance()
                    else -> KeywordTabFragment.newInstance()
                }
            }
        }

        search_tab.setupWithViewPager(search_view_pager)
        search_view_pager.adapter = tabPagerAdapter
        search_view_pager.offscreenPageLimit = 2
        search_view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(search_tab))

        edt_search.setOnEditorActionListener { _, i, _ ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(edt_search.windowToken, 0)
            val searchWord = "${edt_search.text}".trim()
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_GO) {
                if ("${edt_search.text}".isEmpty()) {
                    shortToast(R.string.enter_search)
                } else {
                    supportFragmentManager.fragments.forEach {
                        when (it) {
                            is PlaceNameTabFragment -> it.searchByName(searchWord)
                            is AddressTabFragment -> it.searchByAddress(searchWord)
                            is KeywordTabFragment -> it.searchByKeyword(searchWord)
                        }
                    }
                }
            }
            true
        }

        btn_back.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(edt_search.windowToken, 0)
            finish()
        }

        btn_search.setOnClickListener {
            val searchWord = "${edt_search.text}".trim()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(edt_search.windowToken, 0)
            if ("${edt_search.text}".isEmpty()) {
                shortToast(R.string.enter_search)
            } else {
                supportFragmentManager.fragments.forEach {
                    when (it) {
                        is PlaceNameTabFragment -> it.searchByName(searchWord)
                        is AddressTabFragment -> it.searchByAddress(searchWord)
                        is KeywordTabFragment -> it.searchByKeyword(searchWord)
                    }
                }
            }
        }
    }
}
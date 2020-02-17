package com.yeonae.chamelezone.view.search

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.libraries.places.api.model.Place
import com.google.android.material.tabs.TabLayout
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.view.search.adapter.SearchTabAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), KeywordTabFragment.OnKeywordSelectedListener {
    override fun keywordSelected(keyword: String) {
        edt_search.setText(keyword)
        edt_search.setSelection(edt_search.length())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        search_tab.addTab(search_tab.newTab().setText("장소명"))
        search_tab.addTab(search_tab.newTab().setText("지역명"))
        search_tab.addTab(search_tab.newTab().setText("키워드"))

        val searchTabAdapter = SearchTabAdapter(supportFragmentManager)
        search_view_pager.adapter = searchTabAdapter
        search_view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(search_tab))
        
        edt_search.setOnEditorActionListener { textView, i, keyEvent ->
            if(i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT || i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_GO){
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

        search_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    search_view_pager.currentItem = tab.position
                }
            }

        })

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
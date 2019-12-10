package com.yeonae.chamelezone.view.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.view.search.adapter.SearchTabAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        search_tab.addTab(search_tab.newTab().setText("장소명"))
        search_tab.addTab(search_tab.newTab().setText("지역명"))
        search_tab.addTab(search_tab.newTab().setText("키워드"))

        val searchTabAdapter = SearchTabAdapter(supportFragmentManager)
        search_view_pager.adapter = searchTabAdapter
        search_view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(search_tab))

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

    }
}
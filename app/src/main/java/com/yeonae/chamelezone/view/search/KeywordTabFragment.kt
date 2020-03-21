package com.yeonae.chamelezone.view.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.PlaceItem
import com.yeonae.chamelezone.network.model.KeywordResponse
import com.yeonae.chamelezone.view.place.PlaceDetailActivity
import com.yeonae.chamelezone.view.search.adapter.SearchRvAdapter
import com.yeonae.chamelezone.view.search.presenter.KeywordSearchContract
import com.yeonae.chamelezone.view.search.presenter.KeywordSearchPresenter
import kotlinx.android.synthetic.main.fragment_keyword_tab.*

class KeywordTabFragment : Fragment(), KeywordSearchContract.View {
    private val searchRvAdapter = SearchRvAdapter()
    override lateinit var presenter: KeywordSearchContract.Presenter
    private lateinit var listener: OnKeywordSelectedListener

    interface OnKeywordSelectedListener {
        fun keywordSelected(keyword: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = (context as OnKeywordSelectedListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_keyword_tab, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = KeywordSearchPresenter(
            Injection.placeRepository(), this
        )
        presenter.getKeyword()
        setAdapter()

        searchRvAdapter.setOnClickListener(object : SearchRvAdapter.OnClickListener {
            override fun onClick(place: PlaceItem) {
                val intent = Intent(requireContext(), PlaceDetailActivity::class.java)
                intent.putExtra(PLACE_NAME, place.name)
                intent.putExtra(PLACE_NUMBER, place.placeNumber)
                startActivity(intent)
            }
        })
    }

    override fun showPlaceList(placeList: List<PlaceItem>) {
        layout_no_search.visibility = View.GONE
        layout_has_search.visibility = View.VISIBLE
        searchRvAdapter.addData(placeList)
    }

    override fun showMessage(message: String) {
        layout_no_search.visibility = View.VISIBLE
        layout_has_search.visibility = View.GONE
        tv_message.text = message
    }

    override fun showKeywordList(response: List<KeywordResponse>) {
        btn_keyword_1.text = response[0].keywordName
        btn_keyword_2.text = response[1].keywordName
        btn_keyword_3.text = response[2].keywordName
        btn_keyword_4.text = response[3].keywordName
        btn_keyword_5.text = response[4].keywordName
        btn_keyword_6.text = response[5].keywordName
        btn_keyword_7.text = response[6].keywordName
        btn_keyword_8.text = response[7].keywordName
        btn_keyword_9.text = response[8].keywordName
        btn_keyword_10.text = response[9].keywordName

        btn_keyword_1.setOnClickListener {
            listener.keywordSelected(response[0].keywordName)
            searchByKeyword(response[0].keywordName)
        }

        btn_keyword_2.setOnClickListener {
            listener.keywordSelected(response[1].keywordName)
            searchByKeyword(response[1].keywordName)
        }

        btn_keyword_3.setOnClickListener {
            listener.keywordSelected(response[2].keywordName)
            searchByKeyword(response[2].keywordName)
        }

        btn_keyword_4.setOnClickListener {
            listener.keywordSelected(response[3].keywordName)
            searchByKeyword(response[3].keywordName)
        }

        btn_keyword_5.setOnClickListener {
            listener.keywordSelected(response[4].keywordName)
            searchByKeyword(response[4].keywordName)
        }

        btn_keyword_6.setOnClickListener {
            listener.keywordSelected(response[5].keywordName)
            searchByKeyword(response[5].keywordName)
        }

        btn_keyword_7.setOnClickListener {
            listener.keywordSelected(response[6].keywordName)
            searchByKeyword(response[6].keywordName)
        }

        btn_keyword_8.setOnClickListener {
            listener.keywordSelected(response[7].keywordName)
            searchByKeyword(response[7].keywordName)
        }

        btn_keyword_9.setOnClickListener {
            listener.keywordSelected(response[8].keywordName)
            searchByKeyword(response[8].keywordName)
        }

        btn_keyword_10.setOnClickListener {
            listener.keywordSelected(response[9].keywordName)
            searchByKeyword(response[9].keywordName)
        }
    }

    private fun setAdapter() {
        recycler_keyword_tab.layoutManager = LinearLayoutManager(context)
        recycler_keyword_tab.adapter = searchRvAdapter
    }

    fun searchByKeyword(keyword: String) {
        presenter.searchByKeyword(keyword)
    }

    companion object {
        private const val PLACE_NAME = "placeName"
        private const val PLACE_NUMBER = "placeNumber"
        fun newInstance() = KeywordTabFragment()
    }
}
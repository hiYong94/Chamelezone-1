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
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.view.place.PlaceDetailActivity
import com.yeonae.chamelezone.view.search.adapter.SearchRvAdapter
import com.yeonae.chamelezone.view.search.presenter.SearchContract
import com.yeonae.chamelezone.view.search.presenter.SearchPresenter
import kotlinx.android.synthetic.main.fragment_keyword_tab.*

class KeywordTabFragment : Fragment(), SearchContract.View {
    private val searchRvAdapter = SearchRvAdapter()
    override lateinit var presenter: SearchContract.Presenter
    private lateinit var listener: OnKeywordSelectedListener

    interface OnKeywordSelectedListener {
        fun keywordSelected(keyword: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = (context as OnKeywordSelectedListener)
        if (listener == null) {
            throw ClassCastException("$context must implement OnArticleSelectedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_keyword_tab, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = SearchPresenter(
            Injection.placeRepository(requireContext()), this
        )
        setAdapter()

        btn_cafe.setOnClickListener {
            listener?.keywordSelected("카페")
        }

        btn_exhibition.setOnClickListener {
            listener?.keywordSelected("전시회")
        }

        btn_laundry.setOnClickListener {
            listener?.keywordSelected("빨래방")
        }

        btn_select_shop.setOnClickListener {
            listener?.keywordSelected("편집샵")
        }

        btn_bank.setOnClickListener {
            listener?.keywordSelected("은행")
        }

        btn_book_store.setOnClickListener {
            listener?.keywordSelected("서점")
        }

        btn_lodging.setOnClickListener {
            listener?.keywordSelected("숙소")
        }

        btn_office.setOnClickListener {
            listener?.keywordSelected("오피스")
        }

        btn_garden.setOnClickListener {
            listener?.keywordSelected("식물원")
        }

        btn_restaurant.setOnClickListener {
            listener?.keywordSelected("레스토랑")
        }

        searchRvAdapter.setOnClickListener(object : SearchRvAdapter.OnClickListener {
            override fun onClick(place: PlaceResponse) {
                val intent = Intent(requireContext(), PlaceDetailActivity::class.java)
                intent.putExtra(PLACE_NAME, place.name)
                intent.putExtra(PLACE_NUMBER, place.placeNumber)
                startActivity(intent)
            }
        })
    }

    override fun showPlaceList(placeList: List<PlaceResponse>) {
        searchRvAdapter.addData(placeList)
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
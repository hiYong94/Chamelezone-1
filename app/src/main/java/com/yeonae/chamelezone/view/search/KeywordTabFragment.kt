package com.yeonae.chamelezone.view.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Place
import com.yeonae.chamelezone.data.repository.place.PlaceRepositoryImpl
import com.yeonae.chamelezone.data.source.remote.place.PlaceRemoteDataSourceImpl
import com.yeonae.chamelezone.network.api.RetrofitConnection
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.view.place.PlaceDetailActivity
import com.yeonae.chamelezone.view.search.adapter.SearchRvAdapter
import com.yeonae.chamelezone.view.search.presenter.SearchContract
import com.yeonae.chamelezone.view.search.presenter.SearchPresenter
import kotlinx.android.synthetic.main.fragment_keyword_tab.*

class KeywordTabFragment : Fragment(), SearchContract.View {
    override fun showPlaceList(placeList: List<PlaceResponse>) {

    }

    override lateinit var presenter: SearchContract.Presenter
    private val searchList = arrayListOf(
        Place("구슬모아당구장", "전시회, 카페", "서울 용산구 독서당로 85", "7km"),
        Place("론리드프로젝트", "빨래방, 카페", "서울 용산구 신흥로 78", "10km"),
        Place(
            "하나은행X북바이북",
            "은행, 서점",
            "서울 종로구 새문안로5길 19",
            "13km"
        )
    )
    private val searchRvAdapter = SearchRvAdapter(searchList)

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
            PlaceRepositoryImpl.getInstance(
                PlaceRemoteDataSourceImpl.getInstance(RetrofitConnection.placeService)
            ), this
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
            override fun onClick(place: Place) {
                val intent = Intent(context, PlaceDetailActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun setAdapter() {
        recycler_keyword_tab.layoutManager = LinearLayoutManager(context)
        recycler_keyword_tab.adapter = searchRvAdapter
    }
}
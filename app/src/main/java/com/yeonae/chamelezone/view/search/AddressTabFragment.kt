package com.yeonae.chamelezone.view.search

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
import kotlinx.android.synthetic.main.fragment_address_tab.*

class AddressTabFragment : Fragment(), SearchContract.View {
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_address_tab, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = SearchPresenter(
            PlaceRepositoryImpl.getInstance(
                PlaceRemoteDataSourceImpl.getInstance(RetrofitConnection.placeService)
            ), this
        )
        setAdapter()

        searchRvAdapter.setOnClickListener(object : SearchRvAdapter.OnClickListener {
            override fun onClick(place: Place) {
                val intent = Intent(context, PlaceDetailActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun setAdapter() {
        recycler_address_tab.layoutManager = LinearLayoutManager(context)
        recycler_address_tab.adapter = searchRvAdapter
    }
}
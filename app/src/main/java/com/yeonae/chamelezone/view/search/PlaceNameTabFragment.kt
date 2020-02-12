package com.yeonae.chamelezone.view.search

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
import kotlinx.android.synthetic.main.fragment_place_name_tab.*

class PlaceNameTabFragment : Fragment(), SearchContract.View {
    private val searchRvAdapter = SearchRvAdapter()
    override lateinit var presenter: SearchContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_place_name_tab, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = SearchPresenter(
            Injection.placeRepository(), this
        )
        setAdapter()

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
        recycler_place_name_tab.layoutManager = LinearLayoutManager(context)
        recycler_place_name_tab.adapter = searchRvAdapter
    }

    fun searchByName(name: String) {
        presenter.searchByName(name)
    }

    companion object {
        private const val PLACE_NAME = "placeName"
        private const val PLACE_NUMBER = "placeNumber"
        fun newInstance() = PlaceNameTabFragment()
    }
}
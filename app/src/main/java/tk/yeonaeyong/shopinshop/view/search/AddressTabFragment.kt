package tk.yeonaeyong.shopinshop.view.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_address_tab.*
import tk.yeonaeyong.shopinshop.Injection
import tk.yeonaeyong.shopinshop.R
import tk.yeonaeyong.shopinshop.data.model.PlaceItem
import tk.yeonaeyong.shopinshop.view.place.PlaceDetailActivity
import tk.yeonaeyong.shopinshop.view.search.adapter.SearchRvAdapter
import tk.yeonaeyong.shopinshop.view.search.presenter.SearchContract
import tk.yeonaeyong.shopinshop.view.search.presenter.SearchPresenter

class AddressTabFragment : Fragment(), SearchContract.View {
    private val searchRvAdapter = SearchRvAdapter()
    override lateinit var presenter: SearchContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_address_tab, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = SearchPresenter(
            Injection.placeRepository(), this
        )
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

    private fun setAdapter() {
        recycler_address_tab.layoutManager = LinearLayoutManager(context)
        recycler_address_tab.adapter = searchRvAdapter
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

    fun searchByAddress(address: String) {
        presenter.searchByAddress(address)
    }

    companion object {
        private const val PLACE_NAME = "placeName"
        private const val PLACE_NUMBER = "placeNumber"
        fun newInstance() = AddressTabFragment()
    }
}
package com.yeonae.chamelezone.view.course

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.Injection
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.network.model.PlaceResponse
import com.yeonae.chamelezone.view.course.adapter.PlaceChoiceRvAdapter
import com.yeonae.chamelezone.view.search.presenter.SearchContract
import com.yeonae.chamelezone.view.search.presenter.SearchPresenter
import kotlinx.android.synthetic.main.fragment_place_choice.*

class PlaceChoiceFragment : Fragment(), SearchContract.View {
    override lateinit var presenter: SearchContract.Presenter
    private lateinit var lastCheckedPlace: PlaceResponse
    private val placeChoiceRvAdapter = PlaceChoiceRvAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_place_choice, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = SearchPresenter(
            Injection.placeRepository(), this
        )
        setAdapter()

        placeChoiceRvAdapter.setOnClickListener(object : PlaceChoiceRvAdapter.OnClickListener {
            override fun onClick(place: PlaceResponse) {
                lastCheckedPlace = place
            }
        })

        btn_ok.setOnClickListener {
            if (::lastCheckedPlace.isInitialized) {
                val placeIndex = arguments?.getInt(PLACE_INDEX)
                Log.d("placeIndex", placeIndex.toString())
                if (placeIndex != null) {
                    (activity as? CourseRegisterActivity)?.getVisible(
                        placeIndex,
                        lastCheckedPlace
                    )
                }
                requireActivity().onBackPressed()
            }

        }

        view?.setOnClickListener {
            true
        }

        btn_search.setOnClickListener {
            presenter.searchByName("${edt_search.text}")
        }

        btn_cancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    override fun showPlaceList(placeList: List<PlaceResponse>) {
        layout_no_search.visibility = View.GONE
        layout_has_search.visibility = View.VISIBLE
        placeChoiceRvAdapter.addData(placeList)
    }

    override fun showMessage(message: String) {
        layout_no_search.visibility = View.VISIBLE
        layout_has_search.visibility = View.GONE
        tv_message.text = message
    }

    private fun setAdapter() {
        recycler_place_choice.layoutManager = LinearLayoutManager(context)
        recycler_place_choice.adapter = placeChoiceRvAdapter
    }

    companion object {
        private const val PLACE_INDEX = "placeIndex"

        fun newInstance(
            placeIndex: Int
        ) = PlaceChoiceFragment().apply {
            arguments = Bundle().apply {
                putInt(PLACE_INDEX, placeIndex)
            }

        }
    }
}
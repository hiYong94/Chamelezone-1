package com.yeonae.chamelezone.view.course

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeonae.chamelezone.R
import com.yeonae.chamelezone.data.model.Place
import com.yeonae.chamelezone.view.course.adapter.PlaceChoiceRvAdapter
import kotlinx.android.synthetic.main.activity_course_register.*
import kotlinx.android.synthetic.main.fragment_place_choice.*

class PlaceChoiceFragment : Fragment() {
    private val placeChoiceList = arrayListOf(
        Place("구슬모아당구장", "전시회, 카페", "서울 용산구 독서당로 85"),
        Place(
            "구슬모아당구장", "이연재이연재이연재이연재이연재이연재이연재이연재", "연재네 집은 서울시 강서로18아길 25-6 성재파크빌이다!" +
                    "천안집은 천안시 동남구 문암로 92-27 신세대원룸이다."
        )
    )
    private val placeChoiceRvAdapter = PlaceChoiceRvAdapter(placeChoiceList)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_place_choice, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setAdapter()

        placeChoiceRvAdapter.setOnClickListener(object : PlaceChoiceRvAdapter.OnClickListener {
            override fun onClick(place: Place) {
                btn_ok.setOnClickListener {
                    val visible = arguments!!.getString("visible")
                    (activity as CourseRegisterActivity).getVisible(visible.toString(), place)
                    requireActivity().onBackPressed()
                }
            }
        })

        view?.setOnClickListener {
            true
        }

        btn_search.setOnClickListener {

        }

        btn_cancel.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    private fun setAdapter() {
        recycler_place_choice.layoutManager = LinearLayoutManager(context)
        recycler_place_choice.adapter = placeChoiceRvAdapter
    }

    companion object {
        fun newInstance(
            visible: String
        ) = PlaceChoiceFragment().apply {
            arguments = Bundle().apply {
                putString("visible", visible)
            }

        }
    }

}
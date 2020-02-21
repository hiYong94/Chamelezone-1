package com.yeonae.chamelezone.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yeonae.chamelezone.SingleDialogFragment
import com.yeonae.chamelezone.R
import kotlinx.android.synthetic.main.fragment_find_email.*

class FindEmailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_find_email, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_find_email.setOnClickListener {

        }
        btn_back.setOnClickListener {
            (activity as LoginActivity).back()
        }
    }

    private fun showDialog() {
        val newFragment = SingleDialogFragment.newInstance(
            R.string.information_not_exist
        )
        newFragment.show(fragmentManager!!, "dialog")
    }
}
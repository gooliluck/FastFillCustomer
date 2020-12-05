package com.gooliluck.fastfillcustomer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.gooliluck.fastfillcustomer.R
import kotlinx.android.synthetic.main.fragment_user_detail.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class UserDetailFragment : BaseFragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageButton>(R.id.button_back).setOnClickListener {
            requireActivity().onBackPressed()
        }

        mainViewModel.currentUser.observe(viewLifecycleOwner, { user->
            tv_user_name.text = user.name
            tv_user_birthday.text = user.birthdayString()

        })
    }
}

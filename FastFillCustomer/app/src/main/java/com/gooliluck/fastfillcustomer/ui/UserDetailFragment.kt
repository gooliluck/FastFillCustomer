package com.gooliluck.fastfillcustomer.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gooliluck.fastfillcustomer.R
import com.gooliluck.fastfillcustomer.main.USER_ID_KEY
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

        mainViewModel.currentUser.observe(viewLifecycleOwner, Observer { user->
            tv_user_name.text = user.name
            tv_user_birthday.text = user.birthday
            user.orders?.let {
                ll_order_lists.removeAllViews()
                it.forEach { orderId ->
                    val textView = TextView(requireContext())
                    val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                    textView.layoutParams = params
                    textView.text = orderId.toString()
                    ll_order_lists.addView(textView)
                }
            }
        })
    }
}

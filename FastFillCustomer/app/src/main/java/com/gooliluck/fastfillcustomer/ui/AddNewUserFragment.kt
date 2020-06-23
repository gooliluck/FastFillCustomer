package com.gooliluck.fastfillcustomer.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.gooliluck.fastfillcustomer.R
import kotlinx.android.synthetic.main.fragment_add_new_user.*

class AddNewUserFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_user, container, false)
    }
    private fun setEditText(editText: EditText, text : String){
        editText.setText(text,TextView.BufferType.EDITABLE)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.currentUser.observe(viewLifecycleOwner , Observer { currentUser ->
            currentUser?.let {
                setEditText(et_name,currentUser.name)
                setEditText(et_phone_number,currentUser.phoneNumber)
                setEditText(et_birthday,currentUser.birthday)
                setEditText(et_payment,currentUser.advancePayment.toString())
                setEditText(et_work,currentUser.work)
                setEditText(et_home_desc,currentUser.homeDesc)
                setEditText(et_desc,currentUser.desc)
            }
        })

        btn_save_user.setOnClickListener {
            if (et_name.text.isNotEmpty() && et_phone_number.text.isNotEmpty() && et_birthday.text.isNotEmpty() && et_payment.text.isNotEmpty() &&
                et_work.text.isNotEmpty() && et_home_desc.text.isNotEmpty() && et_desc.text.isNotEmpty()) {
                mainViewModel.tryUpdateUserData(
                    et_name.text.toString(),
                    et_birthday.text.toString(),
                    et_phone_number.text.toString(),
                    et_payment.text.toString().toInt(),
                    et_desc.text.toString(),
                    et_home_desc.text.toString(),
                    et_work.text.toString())
            } else {
                Toast.makeText(requireContext(),"要寫好全部",Toast.LENGTH_LONG).show()
            }
        }
    }
}

package com.gooliluck.fastfillcustomer.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.gooliluck.fastfillcustomer.R
import com.gooliluck.fastfillcustomer.utils.RBDateController
import kotlinx.android.synthetic.main.fragment_add_new_user.*
import java.util.*

class EditUserFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_user, container, false)
    }

    private fun setEditText(editText: EditText, text: String) {
        editText.setText(text, TextView.BufferType.EDITABLE)
    }
    private fun onBackPress(){
        try {
            requireActivity().onBackPressed()
        }catch (e:Exception){

        }
    }
    private fun getNowViewUser(){
        group_loading.visibility = View.VISIBLE
        mainViewModel.fireStoreDB.collection("Reborn").document("Customers")
            .get()
            .addOnSuccessListener {
                group_loading.visibility = View.GONE

            }
            .addOnFailureListener {
                Log.e("jptest","error while getting view user")
                onBackPress()
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isNewUser = try {
            requireArguments().get("newUser") as Boolean
        } catch (e : Exception) {
            Log.e("jptest","some thing is wrong on getting user")
            true
        }
        when(isNewUser) {
            true -> { group_loading.visibility = View.GONE }
            false -> { getNowViewUser() }
        }

        dp_birthday.maxDate = System.currentTimeMillis()
        mainViewModel.currentUser.observe(viewLifecycleOwner, Observer { currentUser ->
            currentUser?.let {
                setEditText(et_name, currentUser.name)
                setEditText(et_phone_number, currentUser.phoneNumber)
                tv_birthday.text = currentUser.birthdayString()
                setEditText(et_payment, currentUser.advancePayment.toString())
                setEditText(et_work, currentUser.work)
                setEditText(et_home_desc, currentUser.homeDesc)
                setEditText(et_desc, currentUser.desc)
            }
        })

        btn_save_user.setOnClickListener {
            val birthday = RBDateController.getDateFromDatePicker(dp_birthday)
            if (et_name.text.isNotEmpty() && et_phone_number.text.isNotEmpty() && birthday != null && et_payment.text.isNotEmpty() &&
                et_work.text.isNotEmpty() && et_home_desc.text.isNotEmpty() && et_desc.text.isNotEmpty()
            ) {
                val calendar = Calendar.getInstance(Locale.TAIWAN)
                calendar.timeInMillis = birthday.time
                mainViewModel.tryUpdateUserData(
                    et_name.text.toString(),
                    calendar,
                    et_phone_number.text.toString(),
                    et_payment.text.toString().toInt(),
                    et_desc.text.toString(),
                    et_home_desc.text.toString(),
                    et_work.text.toString()
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.write_all_to_save),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

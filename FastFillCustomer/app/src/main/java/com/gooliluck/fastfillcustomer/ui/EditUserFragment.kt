package com.gooliluck.fastfillcustomer.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.gooliluck.fastfillcustomer.R
import com.gooliluck.fastfillcustomer.utils.CustomTextWatcher
import com.gooliluck.fastfillcustomer.utils.TextWatcherType
import kotlinx.android.synthetic.main.fragment_add_new_user.*

class EditUserFragment : BaseFragment() {
    var isNewUser = true
    private var oldMap : Map<String,Any>? = null
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

    private fun sendDataToFireStore() {
        val newName = et_name.text.toString()
        val newBirthday = et_birthday.text.toString()
        val newPhoneNumber = et_phone_number.text.toString()
        val newPayment = et_payment.text.toString().toInt()
        val newDesc = et_desc.text.toString()
        val newHomeDesc = et_home_desc.text.toString()
        val newWorkText = et_work.text.toString()
        
    }

    private fun checkDataChanged(map : Map<String,Any>) : Boolean {
        oldMap?.let {
            it.forEach { (s, any) ->
                if (map[s] != any)
                    return false
            }
        }
        return true
    }
    private fun getNowViewUser() {
        group_loading.visibility = View.VISIBLE
        mainViewModel.fireStoreDB.collection("Reborn").document("Customers")
            .get()
            .addOnSuccessListener {
                group_loading.visibility = View.GONE
                oldMap = it.data
            }
            .addOnFailureListener {
                Log.e("jptest","error while getting view user")
                onBackPress()
            }
    }
    private fun initTextWatcher(){
        et_birthday.addTextChangedListener(CustomTextWatcher(et_birthday,TextWatcherType.BirthDay))
        et_payment.addTextChangedListener(CustomTextWatcher(et_payment,TextWatcherType.Currency))
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isNewUser = try {
            requireArguments().get("newUser") as Boolean
        } catch (e : Exception) {
            Log.e("jptest","some thing is wrong on getting user")
            true
        }
        when(isNewUser) {
            true -> { group_loading.visibility = View.GONE }
            false -> { getNowViewUser() }
        }
        initTextWatcher()
        mainViewModel.currentUser.observe(viewLifecycleOwner, { currentUser ->
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
            if (et_name.text.isNotEmpty() && et_phone_number.text.isNotEmpty() && et_birthday.text.isNotEmpty() && et_payment.text.isNotEmpty() &&
                et_work.text.isNotEmpty() && et_home_desc.text.isNotEmpty() && et_desc.text.isNotEmpty()
            ) {
                sendDataToFireStore()
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

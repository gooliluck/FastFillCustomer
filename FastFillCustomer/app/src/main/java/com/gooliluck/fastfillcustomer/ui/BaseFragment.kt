package com.gooliluck.fastfillcustomer.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gooliluck.fastfillcustomer.main.MainViewModel

open class BaseFragment : Fragment(){
    lateinit var mainViewModel : MainViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity(),ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(MainViewModel::class.java)
    }
}
package com.gooliluck.fastfillcustomer.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.gooliluck.fastfillcustomer.data.model.Customer
import com.gooliluck.fastfillcustomer.data.model.Order
import com.gooliluck.fastfillcustomer.data.repository.RebornRepository
import com.gooliluck.fastfillcustomer.ui.model.JPNavControl

const val USER_ID_KEY = "user_id"
enum class UserStatus {
    NONE,LOGIN,SUCCESS
}

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val rebo = RebornRepository.getInstance(application)
    private var mAlertMessage = MutableLiveData<String>()
    val alertMessage : LiveData<String>
        get() = mAlertMessage
    var currentUser = MutableLiveData<Customer>()
    var currentOrder = MutableLiveData<Order>()
    private var mShowFab = MutableLiveData<Boolean>()
    val showFab : LiveData<Boolean>
        get() = mShowFab

    private var mShowFabMenu = MutableLiveData<Boolean>().apply { value = false }
    val showFabMenu : LiveData<Boolean>
        get() = mShowFabMenu

    private var mUserStatus = MutableLiveData<UserStatus>().apply { value = UserStatus.NONE }
    val userStatus : LiveData<UserStatus>
        get() = mUserStatus

    val queryUserList = rebo.queryCustomerList

    private var mNavControl = MutableLiveData<JPNavControl>()
    val navControl : LiveData<JPNavControl>
        get() = mNavControl

    val fireStoreDB = Firebase.firestore

    fun setFabMenuShow(show : Boolean) {
        mShowFabMenu.value = show
    }

    fun setUserStatus(userStatus : UserStatus) {
        mUserStatus.value = userStatus
    }


    fun clearNavControl() {
        mNavControl.value = null
    }

    fun setJPNavControl(jpNavControl: JPNavControl){
        mNavControl.value = jpNavControl
    }


    fun setFabShowing(show : Boolean) {
        mShowFab.value = show
    }
}

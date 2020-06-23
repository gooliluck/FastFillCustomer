package com.gooliluck.fastfillcustomer.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gooliluck.fastfillcustomer.data.model.Order
import com.gooliluck.fastfillcustomer.data.model.User
import com.gooliluck.fastfillcustomer.data.repository.RebornRepository
import com.gooliluck.fastfillcustomer.ui.model.JPNavControl

const val USER_ID_KEY = "user_id"
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val rebo = RebornRepository.getInstance(application)
    private var mAlertMessage = MutableLiveData<String>()
    val alertMessage : LiveData<String>
        get() = mAlertMessage
    val userList = rebo.getAllUsers()
    var currentUser = MutableLiveData<User>()
    var currentOrder = MutableLiveData<Order>()
    private var mShowFab = MutableLiveData<Boolean>()
    val showFab : LiveData<Boolean>
        get() = mShowFab

    val queryUserList = rebo.queryUserList
    val currentDaoUser = rebo.currentDaoUser

    private var mNavControl = MutableLiveData<JPNavControl>()
    val navControl : LiveData<JPNavControl>
        get() = mNavControl

    fun getCurrentAllOrders() : LiveData<List<Order>>? {
        currentUser.value?.let {
            return rebo.getAllOrdersByUser(it.id)
        }
        return null
    }

    fun clearNavControl() {
        mNavControl.value = null
    }

    fun setJPNavControl(jpNavControl: JPNavControl){
        mNavControl.value = jpNavControl
    }

    fun getUserById(userId : Long){
        rebo.getUserById(userId)
    }

    fun getUserByPhoneNumber(phoneNumber: String){
        rebo.searchUserByPhoneNumber(phoneNumber,"")
    }

    fun getUserByName(name: String){
        rebo.searchUserByPhoneNumber("",name)
    }

    fun setFabShowing(show : Boolean) {
        mShowFab.value = show
    }

    fun tryUpdateUserData(name : String?,birthday : String? , phoneNumber : String?, advancePayment : Int?, desc:String?, homeDesc:String?, work:String?){
        Log.e("jptest","updateUserData name:$name ")
        currentUser.value?.let { user ->
            name?.let { user.name = it }
            birthday?.let { user.birthday = it }
            phoneNumber?.let { user.phoneNumber = it }
            advancePayment?.let { user.advancePayment = it }
            desc?.let { user.desc = it }
            homeDesc?.let { user.homeDesc = it }
            work?.let { user.work = it }
            currentUser.postValue(user)
            rebo.updateUser(user)
        } ?: kotlin.run {
            val newUser = User(name = "",  birthday = "", phoneNumber = "", advancePayment = 0, desc= "", homeDesc = "", work = "", orders = null )
            name?.let { newUser.name = it }
            birthday?.let { newUser.birthday = it }
            phoneNumber?.let { newUser.phoneNumber = it }
            advancePayment?.let { newUser.advancePayment = it }
            desc?.let { newUser.desc = it }
            homeDesc?.let { newUser.homeDesc = it }
            work?.let { newUser.work = it }
            rebo.checkUserByInfo(newUser)
        }
    }
}

package com.gooliluck.fastfillcustomer.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gooliluck.fastfillcustomer.data.model.Order
import com.gooliluck.fastfillcustomer.data.model.User
import com.gooliluck.rebornproject.database.RebornDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RebornRepository(application: Application) {
    companion object {
        private var INSTANCE: RebornRepository? = null

        fun getInstance(application: Application) = INSTANCE ?:
        RebornRepository(application).also { INSTANCE = it }
    }
    val rbDao = RebornDatabase.getInstance(application.baseContext).rebornDao
    private var mQueryUserList = MutableLiveData<List<User>>()
    val queryUserList : LiveData<List<User>>
        get() = mQueryUserList
    private var mCurrentDaoUser = MutableLiveData<User>()
    val currentDaoUser : LiveData<User>
        get() = mCurrentDaoUser
    fun getAllUsers() : LiveData<List<User>> {
        return rbDao.getAllUsers()
    }

    fun getAllOrdersByUser(currentId : Long) : LiveData<List<Order>> {
        return rbDao.getOrderByOrderUser(currentId)
    }

    fun updateUser(user: User) {
        rbDao.updateUser(user)
    }

    fun checkUserByInfo(user: User) {
        Log.e("jptest","user name is ${user.name} ${user.phoneNumber}")
        rbDao.getUserByUserInfo(user.phoneNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ users->
                mQueryUserList.value = users
            },{
                Log.e("jptest","error $it")
            }).let {
                Log.e("jptest","disposable $it")
            }
    }

    fun searchUserByPhoneNumber(phoneNumber : String,name:String) {
        rbDao.getUserByUserInfo(phoneNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ users->
                mQueryUserList.value = users
            },{
                Log.e("jptest","error $it")
            }).let {
                Log.e("jptest","disposable $it")
            }
    }

    fun getUserById(userId:Long) {
        rbDao.getUserById(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{

            }).let {

            }
    }
}
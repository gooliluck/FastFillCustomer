package com.gooliluck.fastfillcustomer.data.repository

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.gooliluck.fastfillcustomer.data.model.Order
import com.gooliluck.fastfillcustomer.data.model.User
import com.gooliluck.rebornproject.database.RebornDatabase

class RebornRepository(application: Application) {
    companion object {
        private var INSTANCE: RebornRepository? = null

        fun getInstance(application: Application) = INSTANCE ?:
        RebornRepository(application).also { INSTANCE = it }
    }
    val rbDao = RebornDatabase.getInstance(application.baseContext).rebornDao

    fun getAllUsers() : LiveData<List<User>> {
        return rbDao.getAllUsers()
    }

    fun getAllOrdersByUser(currentId : Long) : LiveData<List<Order>> {
        return rbDao.getOrderByOrderUser(currentId)
    }

    fun updateUser(user: User) {
        rbDao.updateUser(user)
    }

    fun checkUserByInfo(user: User) : List<User> {
        Log.e("jptest","user name is ${user.name} ${user.phoneNumber}")
        return rbDao.getUserByUserInfo(user.name)
    }

    fun getUserById(userId:Long) : User {
        return rbDao.getUserById(userId)
    }
}
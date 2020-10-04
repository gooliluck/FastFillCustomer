package com.gooliluck.fastfillcustomer.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.model.Document
import com.gooliluck.fastfillcustomer.data.model.Order
import com.gooliluck.fastfillcustomer.data.model.User
import com.gooliluck.fastfillcustomer.data.database.RebornDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
const val CUSTOMER_DOCUMENT = "customers"
class RebornRepository(application: Application) {
    companion object {
        private var INSTANCE: RebornRepository? = null

        fun getInstance(application: Application) = INSTANCE ?:
        RebornRepository(application).also { INSTANCE = it }
    }
    private val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val storeCustomerCollectionRef : CollectionReference
    init {
        firebaseFireStore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        storeCustomerCollectionRef = firebaseFireStore.collection(CUSTOMER_DOCUMENT)
    }
    private val rbDao = RebornDatabase.getInstance(application.baseContext).rebornDao
    private var mQueryUserList = MutableLiveData<List<User>>()
    val queryUserList : LiveData<List<User>>
        get() = mQueryUserList
    private var mCurrentDaoUser = MutableLiveData<User>()
    val currentDaoUser : LiveData<User>
        get() = mCurrentDaoUser
    fun getAllUsers() : LiveData<List<User>> {
        getAllUsersOnline()
        return rbDao.getAllUsers()
    }

    fun getAllUsersOnline() {
        storeCustomerCollectionRef.get()
            .addOnSuccessListener {
                for (document in it){
                    Log.e("jptest","${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener {
                Log.d("jptest","get online failed")
            }
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
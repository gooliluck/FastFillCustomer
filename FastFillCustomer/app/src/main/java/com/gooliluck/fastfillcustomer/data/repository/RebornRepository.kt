package com.gooliluck.fastfillcustomer.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.gooliluck.fastfillcustomer.data.model.Customer
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
    private var mQueryUserList = MutableLiveData<List<Customer>>()
    val queryCustomerList : LiveData<List<Customer>>
        get() = mQueryUserList
    private var mCurrentDaoUser = MutableLiveData<Customer>()
    val currentDaoCustomer : LiveData<Customer>
        get() = mCurrentDaoUser

}
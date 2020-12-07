package com.gooliluck.fastfillcustomer.data.model

import java.text.SimpleDateFormat
import java.util.*

data class Customer(var name : String,
                    var birthday : Date,
                    var phoneNumber : String,
                    var advancePayment : Int,
                    var desc : String,
                    var homeDesc : String,
                    var work : String,
                    var firestoreDocId : String
) {
    override fun toString(): String {
        return "Customer(" +
                "name:$name" +
                "birthday:$birthday" +
                "phoneNumber:$phoneNumber" +
                "advancePayment:$advancePayment" +
                "desc:$desc" +
                "homeDesc:$homeDesc" +
                "work:$work" +
                ")"
    }
    fun birthdayString() : String {
        return SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN).format(birthday)
    }
    fun toFireStoreUser() : HashMap<String,Any>{
        val birthdayTime = this.birthday.time

        return hashMapOf(
            "name" to name,"birthdayTime" to birthdayTime,
            "phoneNumber" to phoneNumber,"advancePayment" to advancePayment,
            "desc" to desc, "homeDesc" to homeDesc, "work" to work, "firestoreDocId" to firestoreDocId)
    }
}
data class FireStoreUser(val id: Long = 0,
                         var name : String,
                         var birthday : Long,
                         var phoneNumber : String,
                         var advancePayment : Int,
                         var desc : String,
                         var homeDesc : String,
                         var work : String,
                         var firestoreDocId : String){

    fun toUser() : Customer {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = birthday
        return Customer(name,calendar.time,phoneNumber, advancePayment, desc, homeDesc, work, firestoreDocId)
    }
}


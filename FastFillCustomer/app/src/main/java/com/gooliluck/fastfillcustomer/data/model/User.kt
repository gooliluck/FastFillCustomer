package com.gooliluck.fastfillcustomer.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

const val TABLE_USER = "users"
@Entity (tableName = TABLE_USER)
data class User(@PrimaryKey(autoGenerate = true) val id: Long = 0,
                var name : String,
                var birthday : Date,
                @ColumnInfo(name = "phone_number") var phoneNumber : String,
                @ColumnInfo(name = "advance_payment") var advancePayment : Int,
                var desc : String,
                @ColumnInfo(name = "home_desc") var homeDesc : String,
                var work : String,
                var orders : List<Long>?,
                var firestoreDocId : String
) {
    override fun toString(): String {
        return "User(" +
                "id:$id" +
                "name:$name" +
                "birthday:$birthday" +
                "phoneNumber:$phoneNumber" +
                "advancePayment:$advancePayment" +
                "desc:$desc" +
                "homeDesc:$homeDesc" +
                "work:$work" +
                "orders:$orders" +
                ")"
    }
    fun birthdayString() : String {
        return SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN).format(birthday)
    }
    fun toFireStoreUser() : FireStoreUser{
        val birthdayTime = this.birthday.time
        return FireStoreUser(id,name,birthdayTime,phoneNumber,advancePayment, desc, homeDesc, work, orders, firestoreDocId)
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
                         var orders : List<Long>?,
                         var firestoreDocId : String){

    fun toUser() : User {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = birthday
        return User(id,name,calendar.time,phoneNumber, advancePayment, desc, homeDesc, work, orders, firestoreDocId)
    }
}


package com.gooliluck.fastfillcustomer.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_USER = "users"
@Entity (tableName = TABLE_USER)
data class User(@PrimaryKey(autoGenerate = true) val id: Long = 0,
                var name : String,
                var birthday : String,
                @ColumnInfo(name = "phone_number") var phoneNumber : String,
                @ColumnInfo(name = "advance_payment") var advancePayment : Int,
                var desc : String,
                @ColumnInfo(name = "home_desc") var homeDesc : String,
                var work : String,
                var orders : List<Long>?
){
}


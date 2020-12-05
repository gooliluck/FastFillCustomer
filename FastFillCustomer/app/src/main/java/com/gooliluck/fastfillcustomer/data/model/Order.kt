package com.gooliluck.fastfillcustomer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

const val TABLE_ORDER = "orders"
@Entity(tableName = TABLE_ORDER)
data class Order(@PrimaryKey val id: Long,
                 var dateInLong : Long,
                 var userid : Long,
                 var employeesJson : String,
                 var serviceListJson : String,
                 var desc : String
) {
    override fun toString(): String {
        return "Order(id:$id" +
                "userid:$userid" +
                "employees:${employees()}" +
                "serviceList:${services()}" +
                "desc:$desc)"
    }
    fun employees() : List<Long>{
        val gson = Gson()
        val itemType = object : TypeToken<List<Long>>(){}.type
        return if (employeesJson.isNotEmpty())
            gson.fromJson(employeesJson,itemType)
        else
            emptyList()
    }
    fun services() : List<Long>{
        val gson = Gson()
        val itemType = object : TypeToken<List<Long>>(){}.type
        return if (serviceListJson.isNotEmpty())
            gson.fromJson(serviceListJson,itemType)
        else
            emptyList()
    }
}

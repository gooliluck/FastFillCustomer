package com.gooliluck.fastfillcustomer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

const val TABLE_ORDER = "orders"
@Entity(tableName = TABLE_ORDER)
data class Order(@PrimaryKey val id: Long,
                 var userid : Long,
                 var employees : List<Long>,
                 var serviceList : List<String>,
                 var desc : String
) {
    override fun toString(): String {
        return "Order(id:$id" +
                "userid:$userid" +
                "employees:$employees" +
                "serviceList:$serviceList" +
                "desc:$desc)"
    }
}

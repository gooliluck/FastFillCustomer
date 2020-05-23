package com.gooliluck.fastfillcustomer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

const val TABLE_ORDER = "orders"
@Entity(tableName = TABLE_ORDER)
class Order(@PrimaryKey val id: Long,
            var userid : Long,
            var cut : Int?,
            var dye : Int?,
            var wash : Int?,
            var perm : Int?,
            var date: Long,
            var product : Int?,
            var desc : String
) {
}

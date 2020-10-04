package com.gooliluck.fastfillcustomer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gooliluck.fastfillcustomer.data.model.Order
import com.gooliluck.fastfillcustomer.data.model.User
import com.gooliluck.fastfillcustomer.data.converter.DateConverter
import com.gooliluck.fastfillcustomer.data.converter.StringsConverter
import com.gooliluck.fastfillcustomer.data.converter.LongsConverter
import com.gooliluck.fastfillcustomer.data.model.Employee
import com.gooliluck.fastfillcustomer.data.model.Service

@TypeConverters(DateConverter::class, LongsConverter::class, StringsConverter::class)
@Database(entities = [User::class, Order::class, Service::class, Employee::class], version = 1)
abstract class RebornDatabase : RoomDatabase(){
    abstract val rebornDao: RebornDao

    companion object {
        private var INSTANCE: RebornDatabase? = null

        fun getInstance(context: Context) = INSTANCE ?:
        Room.databaseBuilder(context, RebornDatabase::class.java, "RebornDatabase")
             .allowMainThreadQueries() //don't use, need to in work thread
            .fallbackToDestructiveMigration()
            .build().also { INSTANCE = it }
    }
}
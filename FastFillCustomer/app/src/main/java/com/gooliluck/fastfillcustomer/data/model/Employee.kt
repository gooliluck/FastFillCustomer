package com.gooliluck.fastfillcustomer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gooliluck.fastfillcustomer.data.converter.EnumTypeAdapterFactory
import java.util.*

enum class JobTitle {
    Owner,Junior,Senior
}

const val TABLE_EMPLOYEE = "Employee"
@Entity(tableName = TABLE_EMPLOYEE)
data class Employee(@PrimaryKey val id : Long, val name:String, var jobTitle : String, val birthday: Date, var phone : String, var draw : Double, var baseSalary : Double, var inWork : Boolean){
    companion object {
        fun convertToEmployee(id : Long,name:String,jobTitle : String , birthday: Date, phone : String, draw : Double, baseSalary : Double,inWork: Boolean) : Employee {
            return Employee(id,name,jobTitle,birthday, phone, draw, baseSalary,inWork)
        }
        fun toEmployee(data : String) : Employee? {
            val gson = Gson()
            if (data.isEmpty()) {
                return null
            }
            return gson.fromJson(data, Employee::class.java)
        }
    }
    fun toJsonString() : String {
        val gson = GsonBuilder().registerTypeAdapterFactory(EnumTypeAdapterFactory()).create()
        return gson.toJson(this)
    }

}
package com.gooliluck.fastfillcustomer.data.model

import com.google.gson.Gson

data class Service(val name : String, var cost:Long, var discount:Long, var employeeList: List<String>) {

    companion object {
        fun toService(data : String) : Service? {
            val gson = Gson()
            if (data.isEmpty()) {
                return null
            }
            return gson.fromJson(data, Service::class.java)
        }
    }

    fun toJsonString() : String {
        val gson = Gson()
        return gson.toJson(this)
    }
    override fun toString(): String {
        return "Service(" +
                "name:$name," +
                "cost:$cost," +
                "discount:$discount" +
                "employeeList:$employeeList" +
                ")"
    }

}
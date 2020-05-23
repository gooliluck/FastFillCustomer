package com.gooliluck.rebornproject.database

import android.database.Observable
import androidx.lifecycle.LiveData
import androidx.room.*
import com.gooliluck.fastfillcustomer.data.model.Order
import com.gooliluck.fastfillcustomer.data.model.TABLE_ORDER
import com.gooliluck.fastfillcustomer.data.model.TABLE_USER
import com.gooliluck.fastfillcustomer.data.model.User

@Dao
interface RebornDao {
    /**
     * User section
     * */
    @Query("select * from $TABLE_USER")
     fun getAllUsers(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun updateUser(user: User)

    @Query("SELECT * FROM $TABLE_USER WHERE id = :id")
     fun getUserById(id: Long): User
//    SELECT * FROM user WHERE user_name LIKE :name AND last_name LIKE :last
    @Query("SELECT * FROM $TABLE_USER WHERE phone_number = :phone")
     fun getUserByUserInfo(phone : String): List<User>

    @Delete
     fun deleteUser(user: User)

    /**
     * Order section
     * */
    @Query("select * from $TABLE_ORDER")
    fun getAllOrders(): LiveData<List<Order>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateOrder(order: Order)

    @Query("SELECT * FROM $TABLE_ORDER WHERE 'id' = :id")
    fun getOrderById(id: Long): Order

    @Query("SELECT * FROM $TABLE_ORDER WHERE 'userid' LIKE :userId ORDER BY date")
    fun getOrderByOrderUser(userId : Long): LiveData<List<Order>>

    @Delete
    fun deleteOrder(order: Order)
}



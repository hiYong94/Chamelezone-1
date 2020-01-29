package com.yeonae.chamelezone.network.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yeonae.chamelezone.network.room.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUser(): List<User>

    @Insert
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)
}
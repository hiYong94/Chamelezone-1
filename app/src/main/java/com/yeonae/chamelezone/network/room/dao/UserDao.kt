package com.yeonae.chamelezone.network.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yeonae.chamelezone.network.room.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUser(): UserEntity

    @Query("SELECT COUNT(*) FROM USER")
    fun getUserCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity): Long

    @Query("DELETE FROM user")
    fun deleteUser(): Int
}
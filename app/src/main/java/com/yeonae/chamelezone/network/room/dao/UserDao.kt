package com.yeonae.chamelezone.network.room.dao

import androidx.room.*
import com.yeonae.chamelezone.network.room.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUser(): UserEntity

    @Query("SELECT COUNT(*) FROM USER")
    fun getUserCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity)

    @Query("DELETE FROM user")
    fun deleteUser()
}
package tk.yeonaeyong.shopinshop.network.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM USER")
    fun getUser(): UserEntity

    @Query("SELECT COUNT(*) FROM USER")
    fun getUserCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity): Long

    @Query("UPDATE USER SET nickname = :nickname, phone = :phone WHERE PK = 0")
    fun updateUser(nickname: String, phone: String): Int

    @Query("DELETE FROM USER")
    fun deleteUser(): Int

}
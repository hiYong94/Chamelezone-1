package tk.yeonaeyong.shopinshop.network.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val pk: Int = 0,
    @ColumnInfo(name = "userNumber") val userNumber: Int?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "nickname") val nickname: String?,
    @ColumnInfo(name = "phone") val phone: String?
)
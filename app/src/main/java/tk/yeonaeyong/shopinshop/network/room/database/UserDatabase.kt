package tk.yeonaeyong.shopinshop.network.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import tk.yeonaeyong.shopinshop.network.room.dao.UserDao
import tk.yeonaeyong.shopinshop.network.room.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            return INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java, "user"
            )
                .fallbackToDestructiveMigration()
                .build()
                .also {
                    INSTANCE = it
                }
        }
    }
}
package com.bitgeektalks.little_lemon

// Database.kt

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room

@Entity
data class MenuEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val category: String
)
@Dao
interface MenuDao {
    @Query("SELECT * FROM menuentity")
    suspend fun getMenu(): List<MenuEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenu(menu: List<MenuEntity>)
}

//@Database(entities = [MenuEntity::class], version = 1)
//abstract class MenuDatabase : RoomDatabase() {
//    abstract fun menuDao(): MenuDao
//}
@Database(entities = [MenuEntity::class], version = 1)
abstract class MenuDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao

    companion object {
        private var instance: MenuDatabase? = null

        fun getInstance(context: Context): MenuDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    MenuDatabase::class.java,
                    "menu_database"
                ).build().also { instance = it }
            }
        }
    }
}

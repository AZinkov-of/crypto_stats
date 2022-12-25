package com.example.stats

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [Asset::class]
)

abstract class ADatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao
    companion object {
        @Volatile
        private var INSTANCE: ADatabase? = null

        fun getDatabase(context: Context): ADatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ADatabase::class.java,
                    "asset_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
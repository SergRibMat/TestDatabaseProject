package com.example.testdatabaseproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Person::class, Family::class, Town::class], version = 1,  exportSchema = false)
abstract class TownDatabase : RoomDatabase() {

    abstract val databaseDao: DatabaseDAO

    companion object {

        @Volatile
        private var INSTANCE: TownDatabase? = null

        fun getInstance(context: Context): TownDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TownDatabase::class.java,
                        "town_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance

                }
                return instance
            }
        }
    }

}
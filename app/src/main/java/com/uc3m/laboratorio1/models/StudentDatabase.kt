package com.uc3m.laboratorio1.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class StudentDatabase: RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object{

        @Volatile
        private var INSTANCE: StudentDatabase? = null

        fun getDatabase(context: Context): StudentDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            StudentDatabase::class.java,
                            "student_database"
                    ).fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }

    }

}
package com.example.hvacapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hvacapp.data.dao.*
import com.example.hvacapp.data.entities.*

@Database(
    entities = [
        Istoriko::class,
        Pelatologio::class,
        Peratosi::class,
        Prosopiko::class,
        Synergates::class,
    ],
    version = 6,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun istorikoDao(): IstorikoDao
    abstract fun pelatologioDao(): PelatologioDao
    abstract fun peratosiDao(): PeratosiDao
    abstract fun prosopikoDao(): ProsopikoDao
    abstract fun synergatesDao(): SynergatesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "hvac_database"
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

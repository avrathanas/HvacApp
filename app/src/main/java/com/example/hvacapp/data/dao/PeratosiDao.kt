package com.example.hvacapp.data.dao

import androidx.room.*
import com.example.hvacapp.data.entities.Peratosi
import kotlinx.coroutines.flow.Flow

@Dao
interface PeratosiDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(peratosi: Peratosi)


    @Update
    suspend fun update(peratosi: Peratosi)


    @Delete
    suspend fun delete(peratosi: Peratosi)


    @Query("SELECT * FROM peratosi_table WHERE onoma LIKE :searchQuery OR synergeio LIKE :searchQuery")
    fun searchPeratosi(searchQuery: String): Flow<List<Peratosi>>


    @Query("SELECT * FROM peratosi_table ORDER BY hmerominia DESC")
    fun getAllPeratosi(): Flow<List<Peratosi>>
}
package com.example.hvacapp.data.dao

import androidx.room.*
import com.example.hvacapp.data.entities.Prosopiko
import kotlinx.coroutines.flow.Flow

@Dao
interface ProsopikoDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(ergazomenos: Prosopiko)


    @Update
    suspend fun update(ergazomenos: Prosopiko)


    @Delete
    suspend fun delete(ergazomenos: Prosopiko)


    @Query("SELECT * FROM prosopiko_table WHERE onoma LIKE :searchQuery")
    fun searchProsopiko(searchQuery: String): Flow<List<Prosopiko>>


    @Query("SELECT * FROM prosopiko_table")
    fun getAllProsopiko(): Flow<List<Prosopiko>>
}
package com.example.hvacapp.data.dao

import androidx.room.*
import com.example.hvacapp.data.entities.Istoriko
import kotlinx.coroutines.flow.Flow

@Dao
interface IstorikoDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(entry: Istoriko)


    @Update
    suspend fun update(entry: Istoriko)


    @Delete
    suspend fun delete(entry: Istoriko)


    @Query("SELECT * FROM istoriko_table WHERE onoma LIKE :searchQuery OR odos LIKE :searchQuery")
    fun searchIstoriko(searchQuery: String): Flow<List<Istoriko>>


    @Query("SELECT * FROM istoriko_table")
    fun getAllIstoriko(): Flow<List<Istoriko>>
}
package com.example.hvacapp.data.dao

import androidx.room.*
import com.example.hvacapp.data.entities.Pelatologio
import kotlinx.coroutines.flow.Flow

@Dao
interface PelatologioDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(pelatis: Pelatologio)


    @Update
    suspend fun update(pelatis: Pelatologio)


    @Delete
    suspend fun delete(pelatis: Pelatologio)


    @Query("SELECT * FROM pelatologio_table WHERE onoma LIKE :searchQuery OR thlefono LIKE :searchQuery")
    fun searchPelates(searchQuery: String): Flow<List<Pelatologio>>


    @Query("SELECT * FROM pelatologio_table ORDER BY onoma ASC")
    fun getAllPelates(): Flow<List<Pelatologio>>
}
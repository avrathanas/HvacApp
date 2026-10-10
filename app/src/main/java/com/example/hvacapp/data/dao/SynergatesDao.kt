package com.example.hvacapp.data.dao

import androidx.room.*
import com.example.hvacapp.data.entities.Synergates
import kotlinx.coroutines.flow.Flow

@Dao
interface SynergatesDao {
    @Query("SELECT * FROM synergates_table ORDER BY onoma ASC")
    fun getAll(): Flow<List<Synergates>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(synergates: Synergates)

    @Delete
    suspend fun delete(synergates: Synergates)

    @Query("SELECT * FROM synergates_table WHERE onoma LIKE :searchQuery")
    fun searchSynergates(searchQuery: String): Flow<List<Synergates>>
}

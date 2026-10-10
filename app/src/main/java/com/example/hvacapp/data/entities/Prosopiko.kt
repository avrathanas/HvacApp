package com.example.hvacapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prosopiko_table")
data class Prosopiko(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val onoma: String,
    val ores: Double,
    val kostosYperoria: Double,
    val savvato: Int,
    val synolo: Double
)
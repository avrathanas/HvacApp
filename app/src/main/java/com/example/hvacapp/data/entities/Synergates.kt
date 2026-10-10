package com.example.hvacapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "synergates_table")
data class Synergates(
    @PrimaryKey val onoma: String,
    val thlefono: String
)
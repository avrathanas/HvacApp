package com.example.hvacapp.data.entities

import androidx.room.Entity

@Entity(
    tableName = "pelatologio_table",
    primaryKeys = ["onoma", "dieuthinsi"]
)
data class Pelatologio(
    val onoma: String,
    val dieuthinsi: String,
    val thlefono: String,
    val afm: String,
    val doy: String
)
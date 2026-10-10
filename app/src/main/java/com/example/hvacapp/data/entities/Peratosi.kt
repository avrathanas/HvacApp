package com.example.hvacapp.data.entities

import androidx.room.Entity

@Entity(
    tableName = "peratosi_table",
    primaryKeys = ["onoma", "hmerominia"]
)
data class Peratosi(
    val onoma: String,
    val ergasia: String,
    val synergeio: String,
    val kostos: Double,
    val pliromi: Boolean,
    val oloklirosi: Boolean,
    val hmerominia: String,
    val sxolia: String
)
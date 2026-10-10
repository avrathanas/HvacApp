package com.example.hvacapp.data.entities

import androidx.room.Entity

@Entity(
    tableName = "istoriko_table",
    primaryKeys = ["onoma", "odos", "hmerominia"]
)

data class Istoriko(
    val onoma: String,
    val odos: String,
    val ergasia: String,
    val hmerominia: String,
    val sxolia: String,
    val synergeio: String,
    val synergatis: String
)
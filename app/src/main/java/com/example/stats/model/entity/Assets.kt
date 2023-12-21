package com.example.stats

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asset_table")

data class Asset(
    @PrimaryKey()
    val ticker: String,
    val quantity: Double,
    val total_invested: Double
)

package com.example.robokalam.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "portfolios")
data class Portfolio(
    @PrimaryKey
    val email: String,
    val name: String = "",
    val phone: String = "",
    val skills: String = "",
    val experience: String = "",
    val education: String = "",
    val interests: String = ""
)

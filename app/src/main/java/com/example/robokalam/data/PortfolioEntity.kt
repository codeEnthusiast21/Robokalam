package com.example.robokalam.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "portfolio")
data class PortfolioEntity(
    @PrimaryKey val userId: String,
    val name: String,
    val email: String,
    val phone: String,
    val education: String,
    val skills: String,
    val experience: String
)

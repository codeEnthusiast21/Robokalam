package com.example.robokalam.data.model



//for home fragment RVs
data class Course(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val price: Double,
    val isLive: Boolean = false
)
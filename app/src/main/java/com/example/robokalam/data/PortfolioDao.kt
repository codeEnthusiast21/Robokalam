package com.example.robokalam.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface PortfolioDao {
    @Query("SELECT * FROM portfolios WHERE email = :email")
    suspend fun getPortfolioByEmail(email: String): Portfolio?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(portfolio: Portfolio)
}
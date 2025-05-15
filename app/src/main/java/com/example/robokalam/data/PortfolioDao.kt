package com.example.robokalam.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface PortfolioDao {
    @Query("SELECT * FROM portfolio WHERE userId = :userId")
    fun getPortfolio(userId: String): Flow<PortfolioEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPortfolio(portfolio: PortfolioEntity)
}
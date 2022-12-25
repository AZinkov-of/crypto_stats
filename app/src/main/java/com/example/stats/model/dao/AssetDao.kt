package com.example.stats.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.stats.Asset

@Dao
interface AssetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(asset: Asset)

    @Delete
    suspend fun deleteUser(asset: Asset)

    @Update
    suspend fun updateUser(asset: Asset)

    @Query("SELECT * FROM asset_table")
    fun getAssets(): LiveData<List<Asset>>
}
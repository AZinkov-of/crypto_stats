package com.example.stats.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.stats.Asset

@Dao
interface AssetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAsset(asset: Asset)

    @Delete
    suspend fun deleteAsset(asset: Asset)

    @Update
    suspend fun updateAsset(asset: Asset)

    @Query("SELECT * FROM asset_table")
    fun getAssets(): LiveData<List<Asset>>

    @Query("SELECT * FROM asset_table")
    fun getAssetsN(): List<Asset>
}
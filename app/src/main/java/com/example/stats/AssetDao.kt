package com.example.stats

import androidx.lifecycle.LiveData
import androidx.room.*

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
package com.itis.android_homework.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itis.android_homework.data.local.db.entity.ApiRequestEntity

@Dao
interface ApiRequestDao {

    @Query("SELECT * FROM api_request WHERE id = :id")
    suspend fun getApiRequestById(id: String): ApiRequestEntity?

    @Query("SELECT * FROM api_request")
    suspend fun getAllApiRequests(): List<ApiRequestEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addApiRequest(apiRequestEntity: ApiRequestEntity)

    @Query("DELETE FROM api_request WHERE id = :id")
    suspend fun deleteApiRequestById(id: String)
}
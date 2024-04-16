package com.itis.android_homework.domain.repository

import com.itis.android_homework.domain.model.apirequestmodel.ApiRequestDomainModel

interface ApiRequestRepository {
    suspend fun getApiRequestById(id: String): ApiRequestDomainModel

    suspend fun getAllApiRequests(): List<ApiRequestDomainModel>

    suspend fun addApiRequest(apiRequestDomainModel: ApiRequestDomainModel)

    suspend fun deleteApiRequestById(id: String)
}
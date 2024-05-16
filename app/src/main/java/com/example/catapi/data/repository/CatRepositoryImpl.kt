package com.example.catapi.data.repository

import com.example.catapi.data.datasource.remote.CatRemoteDataSource
import com.example.catapi.domain.model.CatListModel
import com.example.catapi.domain.CatRepository
import kotlinx.coroutines.flow.Flow

internal class CatRepositoryImpl(
    private val remoteDataSource: CatRemoteDataSource
): CatRepository {
    override suspend fun fetchListCatResponse(): Flow<List<CatListModel>> {
        return remoteDataSource.fetchListCat()
    }
}

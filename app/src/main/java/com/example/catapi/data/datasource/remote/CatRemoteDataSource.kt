package com.example.catapi.data.datasource.remote

import com.example.catapi.domain.model.CatListModel
import kotlinx.coroutines.flow.Flow

internal interface CatRemoteDataSource {
    fun fetchListCat(): Flow<List<CatListModel>>
}
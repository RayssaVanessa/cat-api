package com.example.catapi.domain

import com.example.catapi.domain.model.CatListModel
import kotlinx.coroutines.flow.Flow

internal interface CatRepository {
    suspend fun fetchListCatResponse(): Flow<List<CatListModel>>
}
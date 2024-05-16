package com.example.catapi.domain

import com.example.catapi.domain.model.CatListModel
import kotlinx.coroutines.flow.Flow

internal class CatUseCase(
    private val catRepository: CatRepository
) {
    suspend fun getCats(): Flow<List<CatListModel>> {
        return catRepository.fetchListCatResponse()
    }
}
package com.example.catapi.data.datasource.remote

import com.example.catapi.data.mapper.ListMapper
import com.example.catapi.data.api.CatApi
import com.example.catapi.data.mapper.mapToCatListModel
import com.example.catapi.domain.model.CatListModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
private const val API_KEY_VALUE = "live_wsvL9G1XCvqAJAJalLch7fRFXrwHSM3YuCK8WQJuuKNKLIvcFlyPvamffsqgMamU"

internal class CatRemoteDataSourceImpl(
    private val api: CatApi
): CatRemoteDataSource {
    override fun fetchListCat(): Flow<List<CatListModel>> {
        return flow {
            val apiKey = API_KEY_VALUE
            val catListResponse = api.fetchListCatResponse(apiKey).mapToCatListModel()
            emit(catListResponse)
        }
    }
}
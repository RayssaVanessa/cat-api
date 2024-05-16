package com.example.catapi.data.mapper

import com.example.catapi.domain.model.CatListModel
import com.example.catapi.data.response.ListCatResponse

internal  class ListMapper {
    fun mapToCatListModel(catListResponse: List<ListCatResponse>): List<CatListModel> {
        return catListResponse.map { response ->
            CatListModel(
                id = response.id,
                url = response.url
            )
        }
    }
}

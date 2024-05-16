package com.example.catapi.data.mapper

import com.example.catapi.domain.model.CatListModel
import com.example.catapi.data.response.ListCatResponse

internal fun List<ListCatResponse>.mapToCatListModel(): List<CatListModel>  =
     this.map { response ->
        CatListModel(
            id = response.id,
            url = response.url
        )
    }

package com.example.catapi.presentation.viewmodel.state

import com.example.catapi.domain.model.CatListModel

 data class CatState(
    val isLoading: Boolean? = false,
    val isError: String? = null,
    val catList: List<CatListModel>? = emptyList(),
    val messageState: String? = ""
)

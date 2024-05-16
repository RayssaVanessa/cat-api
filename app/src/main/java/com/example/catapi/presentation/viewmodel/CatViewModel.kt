package com.example.catapi.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapi.domain.model.CatListModel
import com.example.catapi.domain.CatUseCase
import com.example.catapi.presentation.viewmodel.action.CatAction
import com.example.catapi.presentation.viewmodel.state.CatState
import kotlinx.coroutines.launch

internal class CatViewModel(
    private val catUseCase: CatUseCase
) : ViewModel() {
    private val _listCat: MutableLiveData<List<CatListModel>> by lazy {
        MutableLiveData<List<CatListModel>>()
    }

    private val _state = MutableLiveData<CatState>()
    val state: LiveData<CatState> get() = _state

    val listCat: LiveData<List<CatListModel>> = _listCat

    init {
        _state.value = CatState()
    }

    fun handleAction(action: CatAction) {
        when (action) {
            is CatAction.LoadData -> loadData()
            is CatAction.onClickedItem -> onClickedItem()

        }
    }

    private fun onClickedItem() {
      _state.value = _state.value?.copy(
          messageState = "Item clicked"
      )
    }

    private fun loadData() {
        starLoading()
        viewModelScope.launch {
            catUseCase.getCats().collect { catList ->
                _state.value = _state.value?.copy(
                    catList = catList)
                            finishLoading()
            }
        }
    }

    private fun starLoading() {
        _state.value = _state.value?.copy(isLoading = true)
    }

    private fun finishLoading() {
        _state.value = _state.value?.copy(isLoading = false)
    }
}
//externalizar a função do glide pra uma extensions fun

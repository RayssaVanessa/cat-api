package com.example.catapi.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapi.data.api.ClientErrorException
import com.example.catapi.data.api.NoConnectivityException
import com.example.catapi.data.api.ServerException
import com.example.catapi.domain.model.CatListModel
import com.example.catapi.domain.CatUseCase
import com.example.catapi.presentation.viewmodel.action.CatAction
import com.example.catapi.presentation.viewmodel.state.CatState
import java.io.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

internal class CatViewModel(
    private val catUseCase: CatUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _state = MutableLiveData<CatState>()
    val state: LiveData<CatState> get() = _state


    init {
        _state.value = CatState()
    }

    fun handleAction(action: CatAction) {
        when (action) {
            is CatAction.LoadData -> loadData()

        }
    }

    fun loadData() {
        startLoading()
        viewModelScope.launch {
            try {
                catUseCase.getCats().collect { catList ->
                    _state.value = _state.value?.copy(
                        catList = catList,
                        isLoading = false
                    )
                }
            } catch (e: IOException) {
                handleError(e)
            } catch (e: HttpException){
                setStateError("erro httpException ${e.code()}")
            }
            finally {
                finishLoading()
            }
        }
    }

    private fun handleError(exception: IOException) {
        when (exception) {
            is NoConnectivityException -> setStateError(exception.message?: "erro de conexão")
            is ClientErrorException -> setStateError(exception.message?: "erro de client")
            is ServerException -> setStateError(exception.message?: "erro de client")
            else -> setStateError(exception.message?: "error")
        }
    }

    private fun setStateError(message: String) {
        _state.postValue(_state.value?.copy(
            isError = message,
            isLoading = false
        ))
    }

    private fun startLoading() {
        _state.postValue(_state.value?.copy(isLoading = true))
    }

    private fun finishLoading() {
        _state.postValue(_state.value?.copy(isLoading = false))
    }
}

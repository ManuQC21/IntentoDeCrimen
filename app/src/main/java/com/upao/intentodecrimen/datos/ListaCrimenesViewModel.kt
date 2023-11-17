package com.upao.intentodecrimen.datos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upao.intentodecrimen.modelo.Crimen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListaCrimenesViewModel: ViewModel() {
    private val crimenRepository=CrimenRepository.get()
    private val _crimenes:MutableStateFlow<List<Crimen>> = MutableStateFlow(emptyList())
    val crimenes: StateFlow<List<Crimen>>
        get() = _crimenes.asStateFlow()

    init{
        viewModelScope.launch {
            crimenRepository.getCrimenes().collect{
                _crimenes.value=it
            }
        }
    }
    suspend fun ingresarCrimen(crimen: Crimen){
        crimenRepository.ingresarCrimen(crimen)
    }
}


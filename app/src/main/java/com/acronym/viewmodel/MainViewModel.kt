package com.acronym.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acronym.repository.Repository
import com.acronym.utils.SearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel(private val repository: Repository): ViewModel() {

    private val _searchState = MutableStateFlow<SearchState>(SearchState.Empty)

    val searchState: StateFlow<SearchState> = _searchState

    fun searchAcronym(query: String) = viewModelScope.launch {
        _searchState.also { state ->
            state.value = SearchState.Loading
            repository.getLongForm(query)
            .flowOn(Dispatchers.IO)
            .catch { e ->
                state.value = SearchState.Error(e.toString())
            }
            .collect {
                state.value = if (it.isEmpty()) SearchState.Empty else SearchState.Success(it)
            }
        }
    }
}

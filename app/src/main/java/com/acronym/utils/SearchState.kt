package com.acronym.utils

import com.acronym.model.AcronymResponse

sealed class SearchState {
    data class Success(val data: AcronymResponse): SearchState()
    data class Error(val message: String?): SearchState()
    object Loading: SearchState()
    object Empty: SearchState()
}
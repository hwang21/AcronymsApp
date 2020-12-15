package com.acronym.repository

import com.acronym.api.ApiService
import kotlinx.coroutines.flow.flow

class RepositoryImpl(private val apiService: ApiService): Repository {

    override fun getLongForm(abbrev: String) = flow {emit(apiService.getLongForm(abbrev))}
}
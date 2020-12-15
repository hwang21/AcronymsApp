package com.acronym.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.acronym.api.ApiService
import com.acronym.repository.RepositoryImpl

class ViewModelFactory(private val apiService: ApiService): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(RepositoryImpl(apiService)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
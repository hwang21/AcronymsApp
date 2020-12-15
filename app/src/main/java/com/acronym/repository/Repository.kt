package com.acronym.repository

import com.acronym.model.AcronymResponse
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getLongForm(abbrev: String): Flow<AcronymResponse>
}
package com.acronym.api

import com.acronym.model.AcronymResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("software/acromine/dictionary.py")
    suspend fun getLongForm(
        @Query("sf") abbreviation: String): AcronymResponse

}
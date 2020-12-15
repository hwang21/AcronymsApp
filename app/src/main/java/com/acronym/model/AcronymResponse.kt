package com.acronym.model

class AcronymResponse: ArrayList<ResponseItem>()

data class ResponseItem(
    val lfs: List<LongForm>,
    val sf: String
)

data class LongForm(
    val freq: Int,
    val lf: String,
    val since: Int,
    val vars: ArrayList<Variation>
)

data class Variation (
    val freq: Int,
    val lf: String,
    val since: Int
)

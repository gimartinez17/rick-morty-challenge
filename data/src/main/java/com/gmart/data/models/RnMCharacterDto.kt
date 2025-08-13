package com.gmart.data.models

data class RnMCharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
    val origin: OriginDto,
    val episode: List<String>,
)

data class OriginDto(
    val name: String,
    val url: String
)

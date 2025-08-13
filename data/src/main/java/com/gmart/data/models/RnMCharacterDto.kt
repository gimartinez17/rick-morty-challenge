package com.gmart.data.models

data class RnMCharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
    val origin: String,
    val episode: List<String>,
)

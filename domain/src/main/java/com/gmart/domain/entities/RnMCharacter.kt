package com.gmart.domain.entities

data class RnMCharacter(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val origin: Origin,
    val gender: String,
    val image: String,
)

data class Origin(
    val name: String,
    val url: String
)

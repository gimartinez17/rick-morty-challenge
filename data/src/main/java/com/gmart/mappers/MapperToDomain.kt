package com.gmart.mappers

import com.gmart.data.models.OriginDto
import com.gmart.data.models.RnMCharacterDto
import com.gmart.domain.entities.Origin
import com.gmart.domain.entities.RnMCharacter

fun RnMCharacterDto.mapToDomain() = RnMCharacter(
    id = id,
    name = name,
    status = status,
    species = species,
    gender = gender,
    origin = origin.mapToDomain(),
    image = image,
)

fun OriginDto.mapToDomain() = Origin(
    name = name,
    url = url
)
package com.example.mypokedex.data.model.response


data class TypeResponse(
    val damage_relations: DamageRelations
)

data class DamageRelations(
    val double_damage_from: List<TypeDetail>,
    val half_damage_from: List<TypeDetail>,
    val no_damage_from: List<TypeDetail>
)

data class TypeDetail(
    val name: String
)

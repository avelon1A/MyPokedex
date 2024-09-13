package com.example.mypokedex.data.model.response


data class EvolutionChainResponse(
    val id: Int,
    val baby_trigger_item: Any?,
    val chain: Chain
)

data class Chain(
    val evolution_details: List<EvolutionDetail>,
    val evolves_to: List<EvolveTo>,
    val species: Species
)

data class EvolveTo(
    val evolution_details: List<EvolutionDetail>,
    val evolves_to: List<EvolveTo>,
    val species: Species
)
data class EvolutionDetail(
    val gender: Any?,
    val held_item: Any?,
    val item: Any?,
    val known_move: Any?,
    val known_move_type: Any?,
    val location: Any?,
    val min_affection: Any?,
    val min_beauty: Any?,
    val min_happiness: Any?,
    val min_level: Int?,
    val needs_overworld_rain: Boolean,
    val party_species: Any?,
    val party_type: Any?,
    val relative_physical_stats: Any?,
    val time_of_day: String,
    val trade_species: Any?,
    val trigger: Trigger,
    val turn_upside_down: Boolean
)

data class Trigger(
    val name: String,
    val url: String
)





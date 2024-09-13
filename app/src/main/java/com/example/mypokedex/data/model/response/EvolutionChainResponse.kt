package com.example.mypokedex.data.model.response


data class EvolutionChainResponse(
    val chain: EvolutionChainNode
)

data class EvolutionChainNode(
    val species: Species,
    val evolves_to: List<EvolutionChainNode>
)



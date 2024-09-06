package com.example.mypokedex.data.model.response

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)
package com.example.mypokedex.data.model.response

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)
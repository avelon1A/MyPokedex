package com.example.mypokedex.util

import com.example.mypokedex.data.model.response.Chain
import com.example.mypokedex.data.model.response.EvolveTo


fun getGender(genderRate: Int): Float {
    return when(genderRate){
        0 -> 100f
        1 -> 87.5f
        2 -> 50f
        3 -> 22.5f
        4 -> 0.01f
        else -> 1f
    }

}
fun getGenderPrecentageMale(genderRate: Int):String{
    return when(genderRate){
        0 -> "100%"
        1 -> "87.5%"
        2 -> "50%"
        3 -> "22.5%"
        4 -> "0%"
        else -> "0"
    }
}
fun getGenderPrecentagefemale(genderRate: Int):String{
    return when(genderRate){
        0 -> "0%"
        1 -> "22.5%"
        2 -> "50%"
        3 -> "87.5%"
        4 -> "100%"
        else -> "0"
    }
}
fun extractPokemonNumberFromUrl(url: String): Int? {
    val regex = """pokemon/(\d+)/""".toRegex()
    val matchResult = regex.find(url)
    return matchResult?.groups?.get(1)?.value?.toIntOrNull()
}
fun removeNewlines(text: String): String {
    return text.replace("\n", " ")
}
fun extractPokemonNumberFromUrl2(url: String): Int? {
    val regex = """pokemon-species/(\d+)/""".toRegex()
    val matchResult = regex.find(url)
    return matchResult?.groups?.get(1)?.value?.toIntOrNull()
}

fun countTotalEvolutions(chain: Chain): Int {
    fun countEvolutionsRecursive(evolvesToList: List<EvolveTo>): Int {
        var count = 0
        for (evolveTo in evolvesToList) {
            count += 1
            count += countEvolutionsRecursive(evolveTo.evolves_to)
        }
        return count
    }
    return countEvolutionsRecursive(chain.evolves_to)
}
fun extractIdFromUrl(url: String): Int? {
    return url.split("/").dropLast(1).last().toIntOrNull()
}
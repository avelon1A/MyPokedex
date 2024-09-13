package com.example.mypokedex.util


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
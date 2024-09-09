package com.example.mypokedex.data.localDataBase



import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromString(value: String?): List<String> {
        return value?.split(",") ?: emptyList()
    }

    @TypeConverter
    fun toString(list: List<String>?): String {
        return list?.joinToString(",") ?: ""
    }
}

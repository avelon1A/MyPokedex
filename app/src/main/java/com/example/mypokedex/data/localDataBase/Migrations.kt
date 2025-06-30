package com.example.mypokedex.data.localDataBase

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE pokemonDB ADD COLUMN favorite INTEGER NOT NULL DEFAULT 0")
    }
}
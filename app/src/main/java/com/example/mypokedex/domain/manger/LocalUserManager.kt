package com.example.mypokedex.domain.manger

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {
	suspend fun saveAppEntry()
	fun readAppEntry(): Flow<Boolean>
	suspend fun loginStatus()
	fun readLoginStatus(): Flow<Boolean>

	
}
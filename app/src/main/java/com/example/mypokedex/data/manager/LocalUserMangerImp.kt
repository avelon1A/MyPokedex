package com.bosch.composewithkotlin20.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.mypokedex.domain.manger.LocalUserManager
import com.example.mypokedex.util.Const
import com.example.mypokedex.util.Const.USER_SETTING

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserMangerImp(private val context: Context): LocalUserManager {

	override suspend fun saveAppEntry() {
	context.dataStore.edit { settings ->
		settings[PrefrencesKeys.APP_ENTRY] = true
	}
	}
	
	override fun readAppEntry(): Flow<Boolean> {
		return context.dataStore.data.map { preferences ->
			preferences[PrefrencesKeys.APP_ENTRY]?: false
		}
	}

	override suspend fun loginStatus() {
		context.dataStore.edit { settings ->
			settings[PrefrencesKeys.LOGING_STATUS] = true
		}
	}

	override fun readLoginStatus(): Flow<Boolean> {
		return context.dataStore.data.map { preferences ->
			preferences[PrefrencesKeys.LOGING_STATUS]?: false
		}
	}
}
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SETTING)
private object PrefrencesKeys {
	val APP_ENTRY = booleanPreferencesKey(name = Const.APP_ENTRY)
	val LOGING_STATUS = booleanPreferencesKey(name = Const.LOGING_STATUS)
}
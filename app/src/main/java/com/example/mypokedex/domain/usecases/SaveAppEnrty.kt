package com.example.mypokedex.domain.usecases

import com.example.mypokedex.domain.manger.LocalUserManager
import kotlinx.coroutines.flow.Flow

class SaveAppEntry (
	 private val localUserManager: LocalUserManager
){
suspend operator fun  invoke(){
	localUserManager.saveAppEntry()
}
}
class GetAppEntry(
	 private val localUserManager: LocalUserManager
) {
	operator fun invoke(): Flow<Boolean> {
		return localUserManager.readAppEntry()
	}
}
data class AppEntryUseCase (
	val saveAppEntry: SaveAppEntry,
	val getAppEntry: GetAppEntry

)
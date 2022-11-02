package com.alejandro.roomDB

import android.app.Application
import com.alejandro.classes.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class UserApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { UserRoomDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { UserRepositoryImpl(database.userDao())}
}
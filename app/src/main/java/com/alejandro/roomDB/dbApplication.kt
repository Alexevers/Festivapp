package com.alejandro.roomDB

import android.app.Application
import com.alejandro.funcFestival.FestivalRepositoryImpl
import com.alejandro.funcTicket.TicketRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Clase que almacena la base de datos y los repositorios con los que se trabaja
 */
class dbApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { AppDatabase.getDatabase(this, applicationScope) }
    val userRepository by lazy { UserRepositoryImpl(database.userDao()) }
    val festivalRepository by lazy { FestivalRepositoryImpl(database.festivalDao())}
    val ticketRepository by lazy { TicketRepositoryImpl(database.ticketDao()) }
}
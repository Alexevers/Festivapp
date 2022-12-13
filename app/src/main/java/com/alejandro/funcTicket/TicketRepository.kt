package com.alejandro.funcTicket

import com.alejandro.classes.Ticket
import com.alejandro.classes.User
import com.alejandro.classes.UserWithFestivals
import com.alejandro.festivapp.Tickets
import java.util.*

interface TicketRepository{
    fun allTickets(): List<UserWithFestivals>
    fun getAllTicketsFromUser(id: Int): List<UserWithFestivals>

    fun deleteTicket(id_user: Int, id_festival:Int): Int
/*

    fun getTicketById(id: Int): Ticket
    fun addTicket(Ticket: Ticket): Long
    fun addTicketList(Tickets: List<Ticket>): List<Long>
    fun update(Ticket: Ticket): Int
    fun deleteAll(): Int
    fun deleteTicket(id: Int): Int

    //ES POSIBLE QUE NO LLEGUEMOS A USAR ESTOS Y SOLO CREEMOS FILTROS EN EL MENÚ DE BUSQUEDA SOBRE TODOS LOS TicketES
    fun getTicketByGenero(genero: String): List<Ticket>
    fun getTicketByArtista(artista: String): List<Ticket>
    fun getTicketProximo(fecha : Date): List<Ticket>
    fun getTicketTerminado() : List<Ticket>

 */
}

class TicketRepositoryImpl constructor(
    private var TicketDao: TicketDao
):TicketRepository{

    override fun allTickets(): List<UserWithFestivals> {
        return TicketDao.getAllTickets()
    }

    override fun getAllTicketsFromUser(id: Int): List<UserWithFestivals> {
        return TicketDao.getAllTicketsFromUser(id)
    }

    override fun deleteTicket(id_user: Int, id_festival: Int): Int {
        return TicketDao.deleteTicket(id_user,id_festival)
    }
}
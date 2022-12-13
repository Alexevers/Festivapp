package com.alejandro.funcFestival

import com.alejandro.classes.Festival
import com.alejandro.classes.Ticket
import java.util.*

interface FestivalRepository{
    fun allFestivals(): List<Festival>
    fun getFestivalById(id: Int): Festival
    fun addTicket(ticket: Ticket): Long

    fun addFestival(festival: Festival): Long
    fun addFestivalList(festivals: List<Festival>): List<Long>
    fun update(festival: Festival): Int
    fun deleteAll(): Int
    fun deleteFestival(id: Int): Int

    //ES POSIBLE QUE NO LLEGUEMOS A USAR ESTOS Y SOLO CREEMOS FILTROS EN EL MENÚ DE BUSQUEDA SOBRE TODOS LOS FESTIVALES
    fun getFestivalByGenero(genero: String): List<Festival>
    fun getFestivalByArtista(artista: String): List<Festival>
    fun getFestivalProximo(fecha : Date): List<Festival>
    fun getFestivalTerminado() : List<Festival>
}

class FestivalRepositoryImpl constructor(
    private var festivalDao: FestivalDao
):FestivalRepository{

    override fun addFestival(festival: Festival): Long {
        return festivalDao.insert(festival)
    }

    override fun deleteFestival(id: Int): Int{
        return festivalDao.deleteFestival(id)
    }

    override fun getFestivalById(id: Int): Festival{
        return festivalDao.getFestivalById(id)
    }
    override fun getFestivalByGenero(genero: String): List<Festival>{
        return festivalDao.getFestivalByGenero(genero)
    }

    override  fun getFestivalByArtista(artista: String): List<Festival>{
        return festivalDao.getFestivalByArtista(artista)
    }

    override fun getFestivalProximo(fecha : Date): List<Festival>{
        return festivalDao.getFestivalProximo(fecha)
    }

    override fun getFestivalTerminado() : List<Festival>{
        return festivalDao.getFestivalTerminado()
    }

    override fun allFestivals(): List<Festival> {
        return festivalDao.getAllFestivals()
    }
    override fun addFestivalList(festivals : List<Festival>): List<Long> {
        return festivalDao.insertFestivalAll(festivals)
    }

    override fun deleteAll():Int {
        return festivalDao.deleteAll()
    }

    override  fun update(festival: Festival): Int{
        return festivalDao.update(festival)
    }

    override fun addTicket(ticket: Ticket): Long {
        return festivalDao.addTicket(ticket)
    }


}
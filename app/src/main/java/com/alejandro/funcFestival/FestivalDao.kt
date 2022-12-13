package com.alejandro.funcFestival

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alejandro.classes.Festival
import com.alejandro.classes.Ticket
import com.alejandro.classes.User
import java.util.Date

@Dao
interface FestivalDao {

    //Seleccionar todos los festivales ordenados por fecha
    @Query("SELECT * FROM tabla_festival ORDER BY fecha_inicio ASC")
    fun getAllFestivals(): List<Festival>

    //Seleccionar festival mediante id
    @Query("SELECT * FROM tabla_festival WHERE id_festival =:id LIMIT 1")
    fun getFestivalById(id: Int): Festival


    @Query("SELECT * FROM tabla_festival WHERE genero =:genero")
    fun getFestivalByGenero(genero: String): List<Festival>


    @Query("select * from tabla_festival where first_artist Like :artista")
    fun getFestivalByArtista(artista: String): List<Festival>

    @Query("select * from tabla_festival where fecha_inicio > :fecha")
    fun getFestivalProximo(fecha : Date): List<Festival>

    @Query("select * from tabla_festival where date(fecha_final,\'unixepoch\') < date(\'now\')")
    fun getFestivalTerminado() : List<Festival>


    //Insertar un nuevo festival
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(festival: Festival): Long

    //for list of festivals insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFestivalAll(festivals: List<Festival>): List<Long>

    //Modificar un festival
    @Update
    fun update(festival: Festival): Int

    //Borrar todos los festivales
    @Query("DELETE FROM tabla_festival")
    fun deleteAll(): Int

    @Query("DELETE FROM tabla_festival where id_festival =:id")
    fun deleteFestival(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTicket(ticket: Ticket):Long


}
package com.alejandro.funcTicket

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.alejandro.classes.Festival
import com.alejandro.classes.Ticket
import com.alejandro.classes.UserWithFestivals
import java.util.Date

@Dao
interface TicketDao {
    //Seleccionar todos los festivales ordenados por fecha
    @Transaction
    @Query("SELECT *  FROM tabla_usuario")
    fun getAllTickets(): List<UserWithFestivals>
    @Transaction
    @Query("SELECT * FROM tabla_usuario where id_user =:id")
    fun getAllTicketsFromUser(id: Int): List<UserWithFestivals>

    @Query("DELETE FROM tabla_ticket where id_user =:id_user AND id_festival =:id_festival")
    fun deleteTicket(id_user: Int,id_festival: Int):Int
    /*
    //Seleccionar festival mediante id
    @Query("SELECT * FROM tabla_festival WHERE id =:id LIMIT 1")
    fun getFestivalById(id: Int): Festival
    //Insertar un nuevo festival
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(festival: Festival): Long


     */
}
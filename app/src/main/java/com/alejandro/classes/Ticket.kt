package com.alejandro.classes

import androidx.room.*

/***
 * Clase que define un usuario del sistema
 */
@Entity(tableName = "tabla_ticket",
//    foreignKeys = [
//        ForeignKey(entity = User::class, parentColumns = arrayOf("id_user"), childColumns = arrayOf("id_user")),
//        ForeignKey(entity = Festival::class, parentColumns = arrayOf("id_festival"), childColumns = arrayOf("id_festival"))
//    ],
    indices = [Index(value = ["id_user","id_festival"], unique = true)])
data class Ticket(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "id_user") val id_user: Int,
    @ColumnInfo(name = "id_festival") val id_festival: Int,
) {



}
package com.alejandro.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date


/***
 * Clase que define un usuario del sistema
 */
@Entity(tableName = "tabla_festival")
data class Festival(
    @PrimaryKey(autoGenerate = true) val id_festival: Int,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "lugar") val lugar: String,
    @ColumnInfo(name = "fecha_inicio") val fecha_inicio: Date,
    @ColumnInfo(name = "fecha_final") val fecha_final: Date,
    //Se podría hacer que el festival tuviera varios artistas, lo que supondría una tabla intermedia y más trabajo
    //Como guardando el artista principal ya tenemos bastante hecho, lo dejamos así
    @ColumnInfo(name = "first_artist") val artistaPrincipal: String,
    @ColumnInfo(name = "descripcion") val descripcion : String,
    //Al igual que con artista, si solo usamos un genero, lo tendremos más facil para buscar y sigue siendo valido
    @ColumnInfo(name = "genero") val genero: String
    ) {



}
package com.alejandro.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


/***
 * Clase que define un usuario del sistema
 */
@Entity(tableName = "tabla_usuario", indices = [Index(value = ["email"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true) val id_user: Int,
    @ColumnInfo(name = "nombre") val nombre: String,
    @ColumnInfo(name = "apellidos") val apellidos: String,
    @ColumnInfo(name = "passwd") val passwd: String,
    @ColumnInfo(name = "email") val email: String,

) {


    fun validatePasswd(passwd: String):Boolean{
        return this.passwd.equals(passwd)
    }
}
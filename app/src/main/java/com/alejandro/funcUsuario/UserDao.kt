package com.alejandro.funcUsuario

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alejandro.classes.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    //Seleccionar todos los usuarios ordenados por nombre
    @Query("SELECT * FROM tabla_usuario ORDER BY nombre ASC")
    fun getAllUsers(): List<User>

    //Seleccionar usuario por email para comprobar que exista dicho email, la clausula unique no termina de funcionar correctamente
    @Query("SELECT * FROM tabla_usuario WHERE email LIKE :email LIMIT 1")
    fun verifyEmail(email: String): List<User>

    //checking user exist or not in our db
    @Query("SELECT * FROM tabla_usuario WHERE email LIKE :email AND passwd LIKE :passwd")
    fun verifyLogin(email: String, passwd: String): User

    //getting user data details
    @Query("select * from tabla_usuario where id_user Like :id")
    fun getUserById(id:Long):User


    //Insertar un nuevo usuario
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User): Long

    //for list of users insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserAll(users: List<User>): List<Long>

    //Modificar un usuario
    @Update
    fun update(user: User): Int

    //Borrar todos los usuarios
    @Query("DELETE FROM tabla_usuario")
    fun deleteAll(): Int
}
package com.alejandro.roomDB

import com.alejandro.classes.User
import com.alejandro.funcUsuario.UserDao

interface UserRepository {
    fun addUser(user: User):Long
    fun addUserList(users: List<User>):List<Long>
    fun deleteUsers():Int
    fun verifyLoginUser(email:String,password:String): User
    fun getUserDataDetails(id:Long):User
    fun verifyEmail(email: String): List<User>
    fun allUsers(): List<User>
    fun updateUser(user: User): Int
}

class UserRepositoryImpl constructor(
    private var userDao: UserDao
):UserRepository{

    override fun addUser(user:User): Long {
        return userDao.insert(user)
    }

    override fun allUsers(): List<User> {
        return userDao.getAllUsers()
    }
    override fun addUserList(users:List<User>): List<Long> {
        return userDao.insertUserAll(users)
    }

    override fun deleteUsers():Int {
        return userDao.deleteAll()
    }

    override fun verifyEmail(email: String):List<User>{
        return userDao.verifyEmail(email)
    }
    override fun verifyLoginUser(email:String,password:String): User {
        return userDao.verifyLogin(email = email, passwd = password )
    }

    override fun getUserDataDetails(id:Long): User {
        return userDao.getUserById(id)
    }

    override fun updateUser(user: User): Int {
        return userDao.update(user)
    }


}
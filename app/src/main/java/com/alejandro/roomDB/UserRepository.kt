package com.alejandro.roomDB

import com.alejandro.classes.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

interface UserRepository {
    fun addUser(user: User):Long
    fun addUserList(users: List<User>):List<Long>
    fun deleteUsers():Int
    fun verifyLoginUser(email:String,password:String): User
    fun getUserDataDetails(id:Long):User
    fun verifyEmail(email: String): List<User>
}

class UserRepositoryImpl constructor(
    private var userDao: UserDao
):UserRepository{

    override fun addUser(user:User): Long {
        return userDao.insert(user)
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




}
package com.alejandro.roomDB

import com.alejandro.classes.User

interface UserRepository {
    fun addUser(user: User):Long
    fun addUserList(users: List<User>):List<Long>
    fun deleteUsers():Int
    fun verifyLoginUser(email:String,password:String): User
    fun getUserDataDetails(id:Long):User
    fun verifyEmail(email: String): List<User>
}

class UserRepositoryImpl constructor(
    private  var usersDao: UserDao,
):UserRepository{

    override fun addUser(user:User): Long {
        return usersDao.insert(user)
    }

    override fun addUserList(users:List<User>): List<Long> {
        return usersDao.insertUserAll(users)
    }

    override fun deleteUsers():Int {
        return usersDao.deleteAll()
    }

    override fun verifyEmail(email: String):List<User>{
        return usersDao.verifyEmail(email)
    }
    override fun verifyLoginUser(email:String,password:String): User {
        return usersDao.verifyLogin(email = email, passwd = password )
    }

    override fun getUserDataDetails(id:Long): User {
        return usersDao.getUserById(id)
    }




}
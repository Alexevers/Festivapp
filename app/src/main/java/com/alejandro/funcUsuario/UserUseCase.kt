package com.alejandro.roomDB

import com.alejandro.classes.User

interface UserUseCase {
    suspend fun addUser(user: User): Long
    suspend fun addUserList(users: List<User>): List<Long>
    suspend fun getUserLoginVerify(email: String, passwd: String): User
    suspend fun getUserData(id:Long): User
    suspend fun deleteUsers():Int
    suspend fun verifyEmail(email: String): List<User>
}


class UsersUseCaseImpl (private var userRepository: UserRepository):UserUseCase{
    override suspend fun addUser(user: User): Long {
        val id= userRepository.addUser(user)
        return id
    }

    override suspend fun addUserList(users: List<User>): List<Long> {
        val id= userRepository.addUserList(users)
        return id
    }

    override suspend fun getUserLoginVerify(email:String, passwd:String): User {
        return userRepository.verifyLoginUser(email, passwd)
    }

    override suspend fun getUserData(id: Long): User {
        return userRepository.getUserDataDetails(id)
    }

    override suspend fun deleteUsers(): Int{
        return userRepository.deleteUsers()
    }
    override suspend fun verifyEmail(email: String): List<User>{
        return userRepository.verifyEmail(email)
    }

}
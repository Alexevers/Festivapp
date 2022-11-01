package com.alejandro.classes


/***
 * Clase que define un usuario del sistema
 */

class User(s: String, s1: String) {

    var email : String = s
     private set
    private var passwd : String = s1

    fun changeEmail(email: String){
        this.email= email
    }

    fun changePasswd(passwd: String,newPasswd: String){
        if(this.passwd.equals(passwd)) this.passwd=newPasswd
    }

    fun validatePasswd(passwd: String):Boolean{
        return this.passwd.equals(passwd)
    }
}
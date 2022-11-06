package com.alejandro.festivapp

/**
 * Esta es la clase para la pantalla del Login
 * En este archivo se exponen todas las funcionalidades de dicha pantalla
 *
 * Funcionalidades:
 * - Loguin del usuario en la aplicacion. Pantalla Login -> Pantalla principal de la aplicacion
 * - Si no existe cuenta de usuario, click en el boton registrar. Pantalla Logiin -> Pantalla Register
 */

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.alejandro.Status.Status

import com.alejandro.roomDB.*


class Login : AppCompatActivity() {
/*
     val PASSWD_PATTERN: Pattern = Pattern.compile(
            "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                //"(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                //"(?=\\S+$)" +           //no white spaces
                //".{4,}" +               //at least 8 characters
                "$"
    )
*/

    private val loginViewModel: LoginViewModel by viewModels(){
        LoginViewModel.LoginViewModelFactory((application as UserApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /**Boton del Login**/
        val btnLogin: Button = findViewById(R.id.Login)
        btnLogin.setOnClickListener {
          btnLoginFunct()
        }
        /**Boton de Register**/
        val btnRegister: Button = findViewById(R.id.btnRegistrar)
        btnRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)

        }

    }
}
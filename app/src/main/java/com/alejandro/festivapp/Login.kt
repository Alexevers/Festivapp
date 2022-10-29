package com.alejandro.festivapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /**Boton del Login**/
        val btn: Button = findViewById(R.id.Login)
        btn.setOnClickListener {
            val intent: Intent = Intent(this, MainScreen::class.java)
            startActivity(intent)
        }
        /**Boton de Register**/
        val btnRegister: Button = findViewById(R.id.btnRegistrar)
        btnRegister.setOnClickListener {
            val intent: Intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}
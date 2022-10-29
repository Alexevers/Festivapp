package com.alejandro.festivapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btn: Button = findViewById(R.id.Login)
        btn.setOnClickListener {
            val intent: Intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        /**boton de Register**/
        val btnRegister: Button = findViewById(R.id.btnRegistrar)
        btnRegister.setOnClickListener {
            val intent: Intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}
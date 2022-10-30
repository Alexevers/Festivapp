package com.alejandro.festivapp

/**
 * Esta es la clase para la pantalla del registro de usuarios
 * En este archivo se exponen todas las funcionalidades de dicha pantalla
 *
 * Funcionalidades:
 * - Click en el boton Registrar -> Registro de datos del usuario en la BBDD -> Pantalla Loguin
 */
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        /**Boton de Register**/
        val btnRegistrar_Register: Button = findViewById(R.id.btnRegistrar_Register)
        btnRegistrar_Register.setOnClickListener {
            val intent: Intent = Intent(this, Login::class.java)
            startActivity(intent)

        }
    }



}
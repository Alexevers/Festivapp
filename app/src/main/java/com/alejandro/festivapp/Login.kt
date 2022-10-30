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
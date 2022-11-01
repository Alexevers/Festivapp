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
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.alejandro.classes.User
import org.w3c.dom.Text

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //vamos a crear un usuario de prueba para la funcionalidad del login
        val user = User("dani\u0040gmail.com","1234")

        /**Boton del Login**/
        val btnLogin: Button = findViewById(R.id.Login)
        btnLogin.setOnClickListener {
            val txtEmail: TextView = findViewById(R.id.Email_Login)
            val txtPasswd: TextView = findViewById(R.id.Password_Login)
            txtEmail.background = ResourcesCompat.getDrawable(resources, R.drawable.sin_borde, null)
            txtPasswd.background = ResourcesCompat.getDrawable(resources, R.drawable.sin_borde, null)

            if(!user.email.equals(txtEmail.text.toString())) {
                Toast.makeText(this, "COMPRUEBA EL EMAIL", Toast.LENGTH_SHORT).show()
                txtEmail.background = ResourcesCompat.getDrawable(resources, R.drawable.borde_rojo, null)
                return@setOnClickListener
            }

            if(!user.validatePasswd(txtPasswd.text.toString())) {
                Toast.makeText(this, "COMPRUEBA LA CONTRASEÑA", Toast.LENGTH_SHORT).show()
                txtPasswd.background = ResourcesCompat.getDrawable(resources, R.drawable.borde_rojo, null)
                return@setOnClickListener
            }

            Toast.makeText(this,"INICIO DE SESION CORRECTO", Toast.LENGTH_SHORT).show()
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
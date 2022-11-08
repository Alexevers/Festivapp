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
import com.alejandro.status.Recurso
import com.alejandro.status.Status
import com.alejandro.classes.User
import com.alejandro.roomDB.*


class Login : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels(){
        LoginViewModel.LoginViewModelFactory((application as UserApplication).repository)
    }




    //este es el "observador" que espera a que el lifedata sea modificado
    var observer = Observer<Recurso<User>> {
        //cuando le live data (en este caso el que guarda el estado del login) se modifica,
        //Guarda el ultimo estado (Post Value), al que se le hace referencia con it
        //It es un "Recurso" que tiene tres datos: status, message y data (que en este caso es un User)
        when (it.status) {
            Status.SUCCESS -> { //si el estado es "success"
                //muestra un toast con el mensage "Superado" (para testeo) TODO = BORRAR LUEGO
                Toast.makeText(this, "it.messageSuccess = ${it.message}", Toast.LENGTH_SHORT).show()

                if (it.data != null) { //Como el query puede haber devuelto algo que no sea un usuario, comprobamos si el data es null
                    //Como en este caso to.do es correcto, almacenamos el usuario logueado en shared preferences bajo el nombre de login
                    val sp = getSharedPreferences("Login", MODE_PRIVATE)
                    val Ed = sp.edit()
                    //Añadimos todos los campos del usuario, para luego poder mostrarlos TODO = Guardar el usuario entero
                    Ed.putString("Umail", it.data.email)
                    Ed.putString("Uname",it.data.nombre)
                    Ed.putString("Psw", it.data.passwd)
                    Ed.apply()
                    Toast.makeText(this, "INICIO DE SESION CORRECTO", Toast.LENGTH_SHORT).show()

                    //El Inicio de SEsion es correcto, te envía al Main Screen

                    val intent = Intent(this, MainScreen::class.java)
                    startActivity(intent)
                    /**
                     * No cerramos la actividad con finish() porque queremos que el usuario pueda volver atrás para loguearse con otra cuenta
                     */

                } else {
                    //Como los datos de usuario no son validos, no se inicia sesión, no podemos determinar si es por la contraseña o por el email
                    Toast.makeText(this, "INICIO DE SESION INCORRECTO", Toast.LENGTH_SHORT).show()
                }

            }
            Status.ERROR -> {
                //Si ha habido algun error (Ha saltado alguna excepción) se muestra por pantalla TODO = Cambiar mensaje por "INTENTELO MÁS TARDE"
                //debugPrintln("it.message = ${it.message}")
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
            }

            else -> { //El tercer estado es "loading", estado que no vamos a usar en este caso puesto que no nos es muy util
                //Si llega la app a entrar aquí significará que ha habido un error de base de datos.
                Toast.makeText(this,"LA BASE DE DATOS NO HA CARGADO CORRECTAMENTE", Toast.LENGTH_LONG).show()
            }

        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /**Boton del Login**/
        val btnLogin: Button = findViewById(R.id.Login)
        btnLogin.setOnClickListener {
            //La función del btn login se ha extraido para mayor limpieza de codigo
            btnLoginFunct()
        }
        /**Boton de Register**/
        val btnRegister: Button = findViewById(R.id.btnRegistrar)
        btnRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)

        }
        /**
         * BOTON DE PRUEBAS, TE ENVIA A UNA ACTIVIDAD DONDE SE MUESTAN LOS USUARIOS REGISTRADOS
         * TODO = ELIMINAR MÁS TARDE
         */
        val tstBtn: Button = findViewById(R.id.tstBtn)
        tstBtn.setOnClickListener{
            val intent= Intent(this, verUsuarios::class.java)

            startActivity(intent)
            Toast.makeText(this, "HAS PULSADO EL BOTON DE PRUEBAS", Toast.LENGTH_SHORT).show()

        }

    }
}
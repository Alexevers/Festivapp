package com.alejandro.festivapp

/**
 * Esta es la clase para la pantalla del Login
 * En este archivo se exponen todas las funcionalidades de dicha pantalla
 *
 * Funcionalidades:
 * - Loguin del usuario en la aplicacion. Pantalla Login -> Pantalla principal de la aplicacion
 * - Si no existe cuenta de usuario, click en el boton registrar. Pantalla Logiin -> Pantalla Register
 */

import android.content.Context
import android.content.Intent
import android.content.res.Resources
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
import com.alejandro.funcUsuario.LoginViewModel
import com.alejandro.roomDB.dbApplication


class Login : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels{
        LoginViewModel.LoginViewModelFactory((application as dbApplication).userRepository)
    }




    //este es el "observador" que espera a que el lifedata sea modificado
    private var observer = Observer<Recurso<User>> {
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
                    val ed = sp.edit()
                    //Añadimos todos los campos del usuario, para luego poder mostrarlos TODO = Guardar el usuario entero
                    ed.putInt("Uid", it.data.id_user)
                    ed.putString("Usurname",it.data.apellidos)
                    ed.putString("Umail", it.data.email)
                    ed.putString("Uname",it.data.nombre)
                    ed.putString("Upass", it.data.passwd)
                    ed.apply()
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

    //On Resume queremos limpiar todos los datos que pueden hacer que la app se "vuelva loca"
    override fun onResume(){
        super.onResume()
        //limpiamos el usuario logueado, puesto que si ha vuelto a esta pantalla es para loguearse
        val sp = getSharedPreferences("Login", MODE_PRIVATE)
        val ed = sp.edit()
        ed.clear()
        ed.apply()

        val txtViewEmail: TextView = findViewById(R.id.Email_Login)
        val txtPasswd: TextView = findViewById(R.id.Password_Login)
        //se pone el borde transparente por si hubiese estado rojo
        txtViewEmail.background = ResourcesCompat.getDrawable(resources, R.drawable.sin_borde, null)
        txtPasswd.background = ResourcesCompat.getDrawable(resources, R.drawable.sin_borde, null)
        //Se limpian los campos del email y password por si acaso
        //TODO = VER SI SE PUEDE PONER EL EMAIL Y EL PASSWORD DEL USUARIO RECIEN REGISTRADO
        txtPasswd.text =""
        txtViewEmail.text = ""
    }
    private fun btnLoginFunct(){
        val txtViewEmail: TextView = findViewById(R.id.Email_Login)
        val txtPasswd: TextView = findViewById(R.id.Password_Login)
        //se pone el borde transparente antes que nada


        txtViewEmail.background = ResourcesCompat.getDrawable(resources, R.drawable.sin_borde, null)
        txtPasswd.background = ResourcesCompat.getDrawable(resources, R.drawable.sin_borde, null)
        //Se validan los campos, si no son validos no se continua
        if(!validarCamposLogin(txtViewEmail,txtPasswd,this,resources)){
            return
        }



        //Carga los datos del inicio de sesión en el live data
        loginViewModel.getUserLoginDataStatus(txtViewEmail.text.toString(),txtPasswd.text.toString())
        try {
            //en principio, solo se leería una vez en caso de ser correcto el login, puesto que se enviaría al usuario a otra activity
            //esperaría hasta que volviese con onResume()
            loginViewModel.getUserLoginDataStatus.observe(this,observer)

        }catch (e: Exception)
        {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }


    companion object {

        fun validarCamposLogin(txtViewEmail: TextView, txtViewPasswd: TextView,context: Context,resources: Resources): Boolean {

            //se valida el email
            val resultEmail = validateEmail(txtViewEmail.text.toString())
            if (resultEmail != "OK") {
                Toast.makeText(context, resultEmail, Toast.LENGTH_SHORT).show()
                //Si falla se pone el borde Rojo

                txtViewEmail.background = ResourcesCompat.getDrawable(resources, R.drawable.borde_rojo, null)
                return false
            }

            //se valida el passwd
            val resultPasswd = validatePasswd(txtViewPasswd.text.toString())
            if (resultPasswd != "OK") {
                Toast.makeText(context, resultPasswd, Toast.LENGTH_SHORT).show()
                //si falla se pone el borde Rojo
                txtViewPasswd.background = ResourcesCompat.getDrawable(resources, R.drawable.borde_rojo, null)
                return false
            }
            return true
        }

        fun validatePasswd(passwd: String): String {
            return when {
                passwd.isEmpty() -> {
                    "Rellena el campo Contraseña"
                }
                //!PASSWD_PATTERN.matcher(txtPasswd.text.toString()).matches()->
                //  "El campo no sigue el formato correcto"
                //}
                else -> {
                    "OK"
                }
            }
        }


       fun validateEmail(email: String): String {

            return when {
                email.isEmpty() -> {
                    "Rellena el campo E-Mail"
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    "El campo no sigue el formato correcto"
                }
                else -> {
                    "OK"
                }
            }
        }
    }
}
package com.alejandro.festivapp

/**
 * Esta es la clase para la pantalla del registro de usuarios
 * En este archivo se exponen todas las funcionalidades de dicha pantalla
 *
 * Funcionalidades:
 * - Click en el boton Registrar -> Registro de datos del usuario en la BBDD -> Pantalla Loguin
 */
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.alejandro.status.Status
import com.alejandro.classes.User
import com.alejandro.festivapp.Login.Companion.validarCamposLogin
import com.alejandro.funcUsuario.RegistroViewModel
import com.alejandro.roomDB.dbApplication
import com.alejandro.status.Recurso

class Register : AppCompatActivity() {

    private val registroViewModel: RegistroViewModel by viewModels{
        RegistroViewModel.RefistroViewModelFactory((application as dbApplication).userRepository)
    }

    //este es el "observador" que espera a que el lifedata sea modificado
    var observer = Observer<Recurso<Long>> {
        //cuando le live data (en este caso el que guarda el estado del registro) se modifica,
        //Guarda el ultimo estado (Post Value), al que se le hace referencia con it
        //It es un "Recurso" que tiene tres datos: status, message y data (que en este caso es un User)
        when (it.status) {
            Status.SUCCESS -> {
                //Toast.makeText(this, "it.messageSuccess = ${it.message}", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "REGISTRO CORRECTO", Toast.LENGTH_SHORT).show()
                val intent: Intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
            Status.ERROR -> {
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
        setContentView(R.layout.activity_register)

        /**Boton de Register
         * se llama Registrar_Register para diferenciarlo del boton de registrar que está en la pantalla de login
         * **/
        val btnRegistrar: Button = findViewById(R.id.btnRegistrar_Register)
        btnRegistrar.setOnClickListener {
            btnRegistrarFunct()


        }
    }

    fun btnRegistrarFunct(){
        val txtEmail: TextView = findViewById(R.id.Email_Register)
        txtEmail.background = ResourcesCompat.getDrawable(resources, R.drawable.sin_borde, null)
        val txtPasswd: TextView = findViewById(R.id.Password_Register)
        txtPasswd.background = ResourcesCompat.getDrawable(resources, R.drawable.sin_borde, null)

        if (validateData(txtEmail, txtPasswd)) return

        try {
            var user = User(0,"Dani","Rosique",txtPasswd.text.toString(),txtEmail.text.toString())
            registroViewModel.insertUserData(user)
            //comprueba el resultado usando el observer declarado anteriormente
            registroViewModel.insertUsersDataStatus.observe(this, observer)
        }catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun validarRegistro(
        txtEmail: TextView,
        txtPasswd: TextView,
        txtNombre: TextView,
        txtApellidos: TextView
    ): Boolean {
        //validar campos login y validar nombre y Apellidos devuelven true si to.do es correcto
        /*
            La tabla logica de esto es la siguiente:

            Validar Login:
                        email=false email=true
            pass=false  false       false
            pass=true   false       true

            validarNombreyApellidos funciona igual.
            El resultado es que todos los campos tienen que ser correctos

         */
       return validarCamposLogin(txtEmail,txtPasswd,this,resources) && validarNombreyApellidos(txtNombre,txtApellidos,this,resources)
    }

    companion object{
        //Lo guardamos en un companion object por si nos fuera a hacer falta en algun otro momento
        //TODO (Seguramente cuando hagamos el update de los datos del usuario nos hará falta)
        /**
         * Devuelve TRUE si to.do es correcto, FALSE si no
         */
        fun validarNombreyApellidos(txtNombre: TextView,txtApellidos:TextView,context: Context,resources: Resources): Boolean{
            //se valida el nombre
            val resultNombre = validateNombre(txtNombre.text.toString())
            if (!resultNombre.equals("OK")) {
                Toast.makeText(context, resultNombre, Toast.LENGTH_SHORT).show()
                //Si falla se pone el borde Rojo

                txtNombre.background = ResourcesCompat.getDrawable(resources, R.drawable.borde_rojo, null)
                return false
            }

            //se validan los apellidos
            val resultSurname = validateSurname(txtApellidos.text.toString())
            if (!resultSurname.equals("OK")) {
                Toast.makeText(context, resultSurname, Toast.LENGTH_SHORT).show()
                //si falla se pone el borde Rojo
                txtApellidos.background = ResourcesCompat.getDrawable(resources, R.drawable.borde_rojo, null)
                return false
            }
            return true
        }


        fun validateSurname(apellidos: String): String {
            return when {
                apellidos.isEmpty() -> {
                    "Rellena el campo Apellidos"
                }
                //Por si queremos que los apellidos sigan algun formato
                // !APELLIDOS_PATTERN.matcher(apellidos.toString()).matches()->
                //  "El campo no sigue el formato correcto"
                //}
                else -> {
                    "OK"
                }
            }
        }


        fun validateNombre(nombre: String): String {
            return when {
                nombre.isEmpty() -> {
                    "Rellena el campo Nombre"
                }
                /*
                POR SI QUEREMOS QUE EL CAMPO NOMBRE SIGA ALGUN PATRÓN, rollo que tenga que empezar por mayusc o lo que sea
                !android.util.Patterns.NOMBRE_PATTERn.matcher(nombre).matches() -> {
                    "El campo no sigue el formato correcto"
                }*/
                else -> {
                    "OK"
                }
            }
        }
    }

}
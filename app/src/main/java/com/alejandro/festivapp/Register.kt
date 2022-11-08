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
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.alejandro.status.Status
import com.alejandro.classes.User
import com.alejandro.roomDB.RegistroViewModel
import com.alejandro.roomDB.UserApplication

class Register : AppCompatActivity() {

    private val registroViewModel: RegistroViewModel by viewModels(){
        RegistroViewModel.RefistroViewModelFactory((application as UserApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        /**Boton de Register**/
        val btnRegistrar_Register: Button = findViewById(R.id.btnRegistrar_Register)
        btnRegistrar_Register.setOnClickListener {

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

        }catch(e:Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }


        try {
            registroViewModel.insertUsersDataStatus.observe(this, Observer {

                when (it.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(
                            this,
                            "it.messageSuccess = ${it.message}",
                            Toast.LENGTH_SHORT
                        ).show()

                            Toast.makeText(this, "REGISTRO CORRECTO", Toast.LENGTH_SHORT).show()
                            val intent: Intent = Intent(this, Login::class.java)

                            startActivity(intent)
                            finish()
                        }

                    Status.ERROR -> {

                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }

                    else -> {

                    }
                }
            })
        }catch (e: Exception)
        {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateData(
        txtEmail: TextView,
        txtPasswd: TextView
    ): Boolean {
        val resultEmail = validateEmail(txtEmail)
        if (!resultEmail.equals("OK")) {
            Toast.makeText(this, resultEmail, Toast.LENGTH_SHORT).show()
            txtEmail.background =
                ResourcesCompat.getDrawable(resources, R.drawable.borde_rojo, null)
            return true
        }
        val resultPasswd = validatePasswd(txtPasswd)
        if (!resultPasswd.equals("OK")) {
            Toast.makeText(this, resultPasswd, Toast.LENGTH_SHORT).show()
            txtPasswd.background =
                ResourcesCompat.getDrawable(resources, R.drawable.borde_rojo, null)
            return true
        }
        return false
    }

    private fun validatePasswd(txtPasswd: TextView): String {
        return when {
            txtPasswd.text.isNullOrEmpty() -> {
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


    private fun validateEmail(txtUserEmail: TextView): String {

        return when {
            txtUserEmail.text.isNullOrEmpty() -> {
                "Rellena el campo E-Mail"
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(txtUserEmail.text.toString()).matches()->{
                "El campo no sigue el formato correcto"
            }
            else -> {
                "OK"
            }
        }
    }


}
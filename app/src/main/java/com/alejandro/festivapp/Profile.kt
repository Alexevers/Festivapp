package com.alejandro.festivapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.alejandro.classes.User
import com.alejandro.funcUsuario.LoginViewModel
import com.alejandro.roomDB.dbApplication
import com.alejandro.status.Status
import org.w3c.dom.Text
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private val loginViewModel: LoginViewModel by viewModels{
        LoginViewModel.LoginViewModelFactory((requireActivity().application as dbApplication).userRepository)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        actualizarDatosUser(view)
        return view
    }

    private fun actualizarDatosUser(view: View) {
        try {
            //TODO Actualizar todos los datos del usuario
            val emailProfile: TextView = view.findViewById(R.id.Email_Profile)
            val btnProfile: Button = view.findViewById(R.id.GuardarCambios_Profile)
            val nombreProfile: TextView =view.findViewById(R.id.Name_Profile)
            val apellidosProfile: TextView =view.findViewById(R.id.Surname_Profile)
            val contraseñaProfile: TextView = view.findViewById(R.id.Password_Profile)
            val sp =  activity?.getSharedPreferences("Login", AppCompatActivity.MODE_PRIVATE);

            emailProfile.text =  sp?.getString("Umail", "")
            nombreProfile.text = sp?.getString("Uname","")
            apellidosProfile.text = sp?.getString("Usurname","")
            contraseñaProfile.text = sp?.getString("Upass","")
            var id = sp?.getInt("Uid",0)
            if(id==null){
                id=0
            }
            btnProfile.setOnClickListener{

                val newName = nombreProfile.text.toString()
                val newSurname = apellidosProfile.text.toString()
                val newPass = contraseñaProfile.text.toString()
                val newEmail = emailProfile.text.toString()
                val ed = sp?.edit()
                //Añadimos todos los campos del usuario, para luego poder mostrarlos TODO = Guardar el usuario entero

                ed?.putString("Usurname",newSurname)
                ed?.putString("Umail", newEmail)
                ed?.putString("Uname",newName)
                ed?.putString("Upass", newPass)
                ed?.apply()


                var newUser : User = User(id,
                    newName,
                    newSurname,
                    newPass,
                    newEmail
                )




                loginViewModel.getUpdateUserStatus(newUser)

                loginViewModel.getUpdateUserStatus.observe(requireActivity(),{
                    recurso->
                    recurso?.let{
                        when(it.status){
                            Status.SUCCESS ->{
                                Toast.makeText(requireActivity(), "DATOS ACTUALIZADOS", Toast.LENGTH_SHORT).show()
                            }
                            Status.ERROR -> {
                                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
        } catch (e: Exception) {
            Toast.makeText(this.activity, "TODO CORRECTO", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
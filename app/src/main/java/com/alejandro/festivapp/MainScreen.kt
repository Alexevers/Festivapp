package com.alejandro.festivapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alejandro.festivapp.databinding.ActivityLoginBinding
import com.alejandro.festivapp.databinding.ActivityMainScreenBinding

class MainScreen : AppCompatActivity() {


    private lateinit var binding: ActivityMainScreenBinding /**esto es necesario para poder vincular las distintas vistas, en este caso, los fragments.
                                                                Usaremos el objeto binding para poder manejarlas
                                                                **/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())



        binding.MainMenu.setOnItemSelectedListener {

            when(it.itemId){
               R.id.menu_home -> replaceFragment(Home())
                R.id.menu_tickets -> replaceFragment(Tickets())
                R.id.menu_perfil -> replaceFragment(Profile())
                R.id.menu_ajustes -> replaceFragment(Settings())

                else ->{

                }

            }
            true
        }

    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction() //se usa para poder moverte entre los fragmentes
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

}
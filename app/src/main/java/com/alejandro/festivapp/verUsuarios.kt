package com.alejandro.festivapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.classes.User
import com.alejandro.roomDB.LoginViewModel
import com.alejandro.roomDB.UserApplication

/**
 * Actividad que permite mostrar todos los usuarios guardados en la base de datos
 */
class verUsuarios : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels(){
        LoginViewModel.LoginViewModelFactory((application as UserApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_usuarios)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        loginViewModel.getAllUsersStatus()
            loginViewModel.getAllUsersStatus.observe(this, Observer { users ->
            // Update the cached copy of the words in the adapter.
            users?.let { adapter.submitList(it) }
        })
    }
}
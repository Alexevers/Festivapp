package com.alejandro.festivapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.classes.User

class UserListAdapter : ListAdapter<User, UserListAdapter.UserViewHolder>(UsersComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.email,current.nombre,current.apellidos,current.id)
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val emailItemView: TextView = itemView.findViewById(R.id.txtEmail)
        private val nombreItemView: TextView = itemView.findViewById(R.id.txtNombre)
        private val apellidosItemView: TextView = itemView.findViewById(R.id.txtApellidos)
        private val idItemView: TextView = itemView.findViewById(R.id.txtIdUser)
        fun bind(email: String?,nombre: String?, apellidos: String?, id: Int?) {
            emailItemView.text = email
            nombreItemView.text = nombre
            apellidosItemView.text = apellidos
            idItemView.text = id.toString()
        }

        companion object {
            fun create(parent: ViewGroup): UserViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recycleviewusers, parent, false)
                return UserViewHolder(view)
            }
        }
    }

    class UsersComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.email == newItem.email
        }
    }
}
package com.alejandro.adapters

import android.R.attr.data
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.classes.Festival
import com.alejandro.classes.Ticket
import com.alejandro.classes.UserWithFestivals
import com.alejandro.festivapp.R
import com.alejandro.funcFestival.FestivalViewModel
import com.alejandro.funcTicket.TicketViewModel
import com.alejandro.status.Recurso
import com.alejandro.status.Status
import java.text.SimpleDateFormat
import java.util.*


class TicketListAdapter
    (val ticketViewModel: TicketViewModel, val id_user: Int, val lifecycleOwner: LifecycleOwner):
    ListAdapter<Festival, TicketListAdapter.TicketViewHolder>(TicketsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        return TicketViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.nombre, current.fecha_inicio)


        holder.itemView.findViewById<Button>(R.id.btnBorrarTicket).setOnClickListener {
            //Toast.makeText(holder.itemView.context, "HAS AÑADIDO El FESTIVAL " + current.id_festival, Toast.LENGTH_SHORT).show()

//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            try {


                ticketViewModel.deleteTicket(id_user, current.id_festival)
                val observer = Observer<Recurso<Int>> {
                    //cuando le live data (en este caso el que guarda el estado del registro) se modifica,
                    //Guarda el ultimo estado (Post Value), al que se le hace referencia con it
                    //It es un "Recurso" que tiene tres datos: status, message y data (que en este caso es un User)
                    when (it.status) {
                        Status.SUCCESS -> {
                            //Toast.makeText(this, "it.messageSuccess = ${it.message}", Toast.LENGTH_SHORT).show()
                            Log.d("correcto","ticket eliminado correctamente")
                            Toast.makeText(holder.itemView.context, "Ticket eliminado", Toast.LENGTH_SHORT).show()
                        }
                        Status.ERROR -> {
                            Log.d("ERROR",it.message.toString())
                            Toast.makeText(holder.itemView.context, it.message, Toast.LENGTH_LONG).show()
                        }
                        else -> { //El tercer estado es "loading", estado que no vamos a usar en este caso puesto que no nos es muy util
                            //Si llega la app a entrar aquí significará que ha habido un error de base de datos.
                            Log.d("ERROR","LA BASE DE DATOS NO SE HA CARGADO")
                            Toast.makeText(
                                holder.itemView.context,
                                "LA BASE DE DATOS NO HA CARGADO CORRECTAMENTE",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
                ticketViewModel.getDeleteTicketStatus.observe(lifecycleOwner,observer )

            }catch (e: Exception){
                Log.d("exception",e.message.toString())
            }

        }

    }

    class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreItemView: TextView = itemView.findViewById(R.id.txtNameTicket)
        private val fechaItemView: TextView = itemView.findViewById(R.id.txtFechaTicket)



        //private val apellidosItemView: TextView = itemView.findViewById(R.id.txtFestName)
        //private val idItemView: TextView = itemView.findViewById(R.id.txtIdUser)
        fun bind(nombre: String?,fecha_inicio: Date?) {

            nombreItemView.text = nombre
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            //val fi = fecha_inicio?.time?.times(1000 )
            fechaItemView.text = formatter.format(fecha_inicio)



        }

        companion object {
            fun create(parent: ViewGroup): TicketViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerviewtickets, parent, false)
                return TicketViewHolder(view)
            }
        }
    }

    class TicketsComparator : DiffUtil.ItemCallback<Festival>() {
        override fun areItemsTheSame(oldItem: Festival, newItem: Festival): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Festival, newItem: Festival): Boolean {
//            return oldItem.festivals == newItem.festivals
            return false
        }
    }
}
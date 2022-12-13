package com.alejandro.festivapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.adapters.FestivalListAdapter
import com.alejandro.adapters.TicketListAdapter
import com.alejandro.funcFestival.FestivalViewModel
import com.alejandro.funcTicket.TicketViewModel
import com.alejandro.roomDB.dbApplication

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Tickets.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tickets : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    val ticketViewModel: TicketViewModel by activityViewModels(){
        TicketViewModel.TicketViewModelFactory((requireActivity().application as dbApplication).ticketRepository)
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
        val view = inflater.inflate(R.layout.fragment_tickets, container, false)
        checkIfFragmentAttached(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        var vista = view
        if(vista != null) {
            checkIfFragmentAttached(vista)
        }
    }

    private fun checkIfFragmentAttached(view: View) {
        if (isAdded && context != null) {
            val sp = activity?.getSharedPreferences("Login", AppCompatActivity.MODE_PRIVATE);
            var id_user =  sp?.getInt("Uid",0)
            if(id_user == null)id_user=0
            val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerviewTickets)
            val adapter = TicketListAdapter(ticketViewModel, id_user,this)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(view.context)



            try {


                ticketViewModel.getTicketsByUserId(id_user)
//                Toast.makeText(view.context, "Cargados los festivals", Toast.LENGTH_SHORT).show()
                ticketViewModel.getTicketsByUserId.observe(
                    requireActivity(),
                    Observer { tickets ->
                        // Update the cached copy of the words in the adapter.
                        tickets?.let {
                            //  Toast.makeText(view.context, it[0].nombre, Toast.LENGTH_SHORT).show()
                            tickets
                            Log.d("Debug","Tamaño tickets"+it.size)
                            adapter.submitList(it[0].festivals)
                        }
                    })
            }catch (e: Exception){
                Log.d("Exception", e.message.toString())
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Tickets.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Tickets().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
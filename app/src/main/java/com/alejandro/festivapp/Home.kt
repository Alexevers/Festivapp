package com.alejandro.festivapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.adapters.FestivalListAdapter
import com.alejandro.adapters.UserListAdapter
import com.alejandro.funcFestival.FestivalViewModel
import com.alejandro.roomDB.dbApplication

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //ViewModel que va a cargar los datos de los festivales
    val festivalViewModel: FestivalViewModel by activityViewModels(){
        FestivalViewModel.FestivalViewModelFactory((requireActivity().application as dbApplication).festivalRepository)
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
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        actualizarDatosUser(view)
        checkIfFragmentAttached(view)
        return view
    }

    private fun checkIfFragmentAttached(view: View) {
              if (isAdded && context != null) {
                                val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerviewFestivals)
                  val sp =  activity?.getSharedPreferences("Login", AppCompatActivity.MODE_PRIVATE)

                  var id_user =  sp?.getInt("Uid",0)
                  if(id_user == null)id_user=0
                    
                  val adapter = FestivalListAdapter(festivalViewModel,id_user,this)
                                recyclerView.adapter = adapter
                                recyclerView.layoutManager = LinearLayoutManager(view.context)

                                festivalViewModel.getAllFestivalsStatus()
//                                Toast.makeText(view.context, "Cargados los festivals", Toast.LENGTH_SHORT).show()
                                festivalViewModel.getAllFestivalsStatus.observe(
                                    requireActivity(),
                                    Observer { festivals ->
                                        // Update the cached copy of the words in the adapter.
                                        festivals?.let {
//                                        Toast.makeText(view.context, it[0].nombre, Toast.LENGTH_SHORT).show()
                                        adapter.submitList(it) }
                    })

        }
    }

    private fun actualizarDatosUser(view: View) {
        try {
            var mensajeBienvenida: TextView = view.findViewById(R.id.txtWelcome)
            val sp =  activity?.getSharedPreferences("Login", AppCompatActivity.MODE_PRIVATE)

            mensajeBienvenida.text = "Bienvenido " + sp?.getString("Uname","" ).toString()

        } catch (e: Exception) {
            Toast.makeText(this.activity, e.message, Toast.LENGTH_LONG).show()
            println(e.message)
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }



}
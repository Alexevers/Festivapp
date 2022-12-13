package com.alejandro.funcTicket

import android.util.Log
import androidx.lifecycle.*
import com.alejandro.classes.Ticket
import com.alejandro.classes.UserWithFestivals
import com.alejandro.status.Recurso
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * ViewModel que controla  de los Ticketes
 */
class TicketViewModel  constructor(var repository: TicketRepository): ViewModel() {
    //Almacena el estado del login
    private val _GetTicketById = MutableLiveData<Recurso<UserWithFestivals>>()

    val getTicketById: MutableLiveData<Recurso<UserWithFestivals>> = _GetTicketById

    val _GetAllTicketsStatus = MutableLiveData<List<UserWithFestivals>?>()

    val getAllTicketsStatus: MutableLiveData<List<UserWithFestivals>?> = _GetAllTicketsStatus

    private val _GetTicketsByUserId = MutableLiveData<List<UserWithFestivals>?>()

    val getTicketsByUserId: MutableLiveData<List<UserWithFestivals>?> = _GetTicketsByUserId

    private val _GetDeleteTicketStatus = MutableLiveData<Recurso<Int>>()

    val getDeleteTicketStatus : MutableLiveData<Recurso<Int>> = _GetDeleteTicketStatus

    /**
     * Funcion que carga todos los Ticketes existentes
     */
    fun getAllTicketsStatus() {
        //Lanza una rutina
        viewModelScope.async {
            try {
                val data = repository.allTickets()
                _GetAllTicketsStatus.postValue(data)

            } catch (exception: Exception) {
                _GetAllTicketsStatus.postValue(null)
            }
        }
    }

    fun getTicketsByUserId(id: Int) {
        //Lanza una rutina
        viewModelScope.async {
            try {
                val data = repository.getAllTicketsFromUser(id)
                _GetTicketsByUserId.postValue(data)

            } catch (exception: Exception) {
                Log.d("Exception", exception.message.toString())
                _GetTicketsByUserId.postValue(null)
            }
        }
    }

    fun deleteTicket(id_user: Int,id_festival: Int) {
        viewModelScope.launch {
            _GetDeleteTicketStatus.postValue(Recurso.loading(null))
            try {
                val data = repository.deleteTicket(id_user,id_festival)
                _GetDeleteTicketStatus.postValue(Recurso.success(data, 0))
                return@launch
            } catch (exception: Exception) {
                _GetDeleteTicketStatus.postValue(Recurso.error(null,"Ticket ya Existe"))
            }
        }
    }

    class TicketViewModelFactory(private val repository: TicketRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TicketViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TicketViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}

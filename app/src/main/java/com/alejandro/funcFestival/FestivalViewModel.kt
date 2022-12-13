package com.alejandro.funcFestival

import androidx.lifecycle.*
import com.alejandro.classes.Festival
import com.alejandro.classes.Ticket
import com.alejandro.status.Recurso
import com.alejandro.classes.User
import com.alejandro.roomDB.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
/**
 * ViewModel que controla  de los festivales
 */
class FestivalViewModel  constructor(var repository: FestivalRepository): ViewModel() {
    //Almacena el estado del login
    private val _GetFestivalById = MutableLiveData<Recurso<Festival>>()

    val getFestivalById: MutableLiveData<Recurso<Festival>> = _GetFestivalById

    val _GetAllFestivalsStatus = MutableLiveData<List<Festival>?>()

    val getAllFestivalsStatus: MutableLiveData<List<Festival>?> = _GetAllFestivalsStatus

    private val _insertTicketDataStatus = MutableLiveData<Recurso<Long>>()

    val insertTicketDataStatus: LiveData<Recurso<Long>> = _insertTicketDataStatus


    /**
     * Funcion que carga todos los festivales existentes
     */
    fun getAllFestivalsStatus() {
        //Lanza una rutina
        viewModelScope.async {
            //No nos interesa el tipo de Recurso loading, puesto que no es necesario esperar a que conecte
            // _GetUserLoginDataStatus.postValue(Recurso.loading(null))
            try {
                val data = repository.allFestivals()
                _GetAllFestivalsStatus.postValue(data)

            } catch (exception: Exception) {
                _GetAllFestivalsStatus.postValue(null)
            }
        }
    }

    fun getFestivalById(id: Int){
        viewModelScope.async {
            //No nos interesa el tipo de Recurso loading, puesto que no es necesario esperar a que conecte
            // _GetUserLoginDataStatus.postValue(Recurso.loading(null))
            try {
                val data = repository.getFestivalById(id)
                _GetFestivalById.postValue(Recurso.success(data,1))

            } catch (exception: Exception) {
                _GetFestivalById.postValue(Recurso.error(null, exception.message!!))
            }
        }
    }

    fun insertTicketData(ticket: Ticket) {
        viewModelScope.launch {
            _insertTicketDataStatus.postValue(Recurso.loading(null))
            try {
                    val data = repository.addTicket(ticket)
                    _insertTicketDataStatus.postValue(Recurso.success(data, 0))
                    return@launch
            } catch (exception: Exception) {
                _insertTicketDataStatus.postValue(Recurso.error(null,"Ticket ya Existe"))
            }
        }
    }
    class FestivalViewModelFactory(private val repository: FestivalRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FestivalViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FestivalViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}

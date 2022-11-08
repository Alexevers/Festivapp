package com.alejandro.roomDB

import androidx.lifecycle.*
import com.alejandro.status.Recurso
import com.alejandro.classes.User
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * ViewModel que controla el login de los usuarios
 */
class LoginViewModel  constructor(var repository: UserRepository): ViewModel() {
    //Almacena el estado del login
    private val _GetUserLoginDataStatus = MutableLiveData<Recurso<User>>()

    val getUserLoginDataStatus: MutableLiveData<Recurso<User>> = _GetUserLoginDataStatus

    val _GetAllUsersStatus = MutableLiveData<List<User>?>()

    val getAllUsersStatus: MutableLiveData<List<User>?> = _GetAllUsersStatus

    //Almacena los datos del usuario logueado
    private val _GetUserProfileDataStatus = MutableLiveData<Recurso<User>>()

    val getUserProfileDataStatus: MutableLiveData<Recurso<User>> = _GetUserProfileDataStatus




    //private val _getNetworkDataStatus = MutableLiveData<Recurso<NetworkResponse>>()

    //val getNetworkDataStatus: MutableLiveData<Resource<NetworkResponse>> = _getNetworkDataStatus
    //Esta función se conecta con la base de datos para obtener el resultado del Login del usuario
    fun getUserLoginDataStatus(email:String,password:String) {
        //Lanza una
        viewModelScope.async {
            //No nos interesa el tipo de Recurso loading, puesto que no es necesario esperar a que conecte
            // _GetUserLoginDataStatus.postValue(Recurso.loading(null))
            try {
                val data = repository.verifyLoginUser(email,password)
                _GetUserLoginDataStatus.postValue(Recurso.success(data,1))
            } catch (exception: Exception) {
                _GetUserLoginDataStatus.postValue(Recurso.error(null, exception.message!!))
            }
        }
    }

    fun getAllUsersStatus() {
        //Lanza una rutina
        viewModelScope.async {
            //No nos interesa el tipo de Recurso loading, puesto que no es necesario esperar a que conecte
            // _GetUserLoginDataStatus.postValue(Recurso.loading(null))
            try {
                val data = repository.allUsers()
                _GetAllUsersStatus.postValue(data)

            } catch (exception: Exception) {
                _GetAllUsersStatus.postValue(null)
            }
        }
    }


    fun getUserProfileData(id:Long){
        viewModelScope.launch {
            _GetUserProfileDataStatus.postValue(Recurso.loading(null))
            try {
                val data = repository.getUserDataDetails(id)
                _GetUserProfileDataStatus.postValue(Recurso.success(data, 0))
            } catch (exception: Exception) {
                _GetUserProfileDataStatus.postValue(Recurso.error(null, exception.message!!))
            }
        }
    }



    class LoginViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
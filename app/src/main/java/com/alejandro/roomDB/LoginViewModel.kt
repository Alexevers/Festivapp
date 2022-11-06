package com.alejandro.roomDB

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alejandro.Status.Recurso
import com.alejandro.classes.User
import com.android.volley.NetworkResponse
import kotlinx.coroutines.launch

/**
 * ViewModel que controla el login de los usuarios
 */
class LoginViewModel  constructor(var repository: UserRepository): ViewModel() {
    //Almacena el estado del login
    private val _GetUserLoginDataStatus = MutableLiveData<Recurso<User>>()

    val getUserLoginDataStatus: MutableLiveData<Recurso<User>> = _GetUserLoginDataStatus

    //Almacena los datos del usuario logueado
    private val _GetUserProfileDataStatus = MutableLiveData<Recurso<User>>()

    val getUserProfileDataStatus: MutableLiveData<Recurso<User>> = _GetUserProfileDataStatus


    //private val _getNetworkDataStatus = MutableLiveData<Recurso<NetworkResponse>>()

    //val getNetworkDataStatus: MutableLiveData<Resource<NetworkResponse>> = _getNetworkDataStatus

    fun getUserLoginDataStatus(email:String,password:String) {
        viewModelScope.launch {
            _GetUserLoginDataStatus.postValue(Recurso.loading(null))
            try {
                val data = repository.verifyLoginUser(email,password)
                _GetUserLoginDataStatus.postValue(Recurso.success(data,1))
            } catch (exception: Exception) {
                _GetUserLoginDataStatus.postValue(Recurso.error(null, exception.message!!))
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
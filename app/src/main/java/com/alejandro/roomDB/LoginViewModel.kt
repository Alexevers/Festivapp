package com.alejandro.roomDB

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.Status.Recurso
import com.alejandro.classes.User
import com.android.volley.NetworkResponse
import kotlinx.coroutines.launch

/**
 * ViewModel que controla el login de los usuarios
 */
class LoginViewModel  constructor(var usersUseCase: UserUseCase): ViewModel() {
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
                val data = usersUseCase.getUserLoginVerify(email,password)
                _GetUserLoginDataStatus.postValue(Recurso.success(data, 0))
            } catch (exception: Exception) {
                _GetUserLoginDataStatus.postValue(Recurso.error(null, exception.message!!))
            }
        }
    }

    fun getUserProfileData(id:Long){
        viewModelScope.launch {
            _GetUserProfileDataStatus.postValue(Recurso.loading(null))
            try {
                val data = usersUseCase.getUserData(id)
                _GetUserProfileDataStatus.postValue(Recurso.success(data, 0))
            } catch (exception: Exception) {
                _GetUserProfileDataStatus.postValue(Recurso.error(null, exception.message!!))
            }
        }
    }


}
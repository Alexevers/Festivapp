package com.alejandro.roomDB

import androidx.lifecycle.*
import com.alejandro.status.Recurso
import com.alejandro.classes.User
import kotlinx.coroutines.launch

/**
 * ViewModel que controla el registro de los usuarios
 */
class RegistroViewModel constructor(private var repository: UserRepository) : ViewModel() {

    //Guarda el estado del registro de un unico usuario
    private val _insertUsersDataStatus = MutableLiveData<Recurso<Long>>()

    val insertUsersDataStatus: LiveData<Recurso<Long>> = _insertUsersDataStatus
    //Guarda el estado del registro de multiples usuarios (util para poblar la BD)
    private val _insertUsersDataStatusList = MutableLiveData<Recurso <List<Long>>>()

    val insertUsersDataStatusList: LiveData<Recurso<Long>> = _insertUsersDataStatus

    fun insertUserData(user: User) {
        viewModelScope.launch {
            _insertUsersDataStatus.postValue(Recurso.loading(null))
            try {
                var noEmail = repository.verifyEmail(user.email)

                if(!noEmail.isNullOrEmpty()){
                    _insertUsersDataStatus.postValue(Recurso.error(null,"Email Ya Existe"))
                    return@launch
                }

                var data = repository.addUser(user)
                _insertUsersDataStatus.postValue(Recurso.success(data, 0))
            } catch (exception: Exception) {
                _insertUsersDataStatus.postValue(Recurso.error(null, exception.message!!))
            }
        }
    }

//for inserting list of users TODO = USARLO para poblar la app

    fun insertUserDataList(users: List<User>) {
        viewModelScope.launch {
            _insertUsersDataStatusList.postValue(Recurso.loading(null))
            try {
                val data = repository.addUserList(users)
                _insertUsersDataStatusList.postValue(Recurso.success(data, 0))
            } catch (exception: Exception) {
                _insertUsersDataStatusList.postValue(Recurso.error(null, exception.message!!))
            }
        }
    }

    class RefistroViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegistroViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RegistroViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
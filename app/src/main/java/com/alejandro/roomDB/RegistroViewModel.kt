package com.alejandro.roomDB

import androidx.lifecycle.*
import com.alejandro.Status.Recurso
import com.alejandro.classes.User
import kotlinx.coroutines.launch

/**
 * ViewModel que controla el registro de los usuarios
 */
class RegistroViewModel constructor(private var usersUseCase: UserUseCase) : ViewModel() {

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
                val data = usersUseCase.addUser(user)
                _insertUsersDataStatus.postValue(Recurso.success(data, 0))
            } catch (exception: Exception) {
                _insertUsersDataStatus.postValue(Recurso.error(null, exception.message!!))
            }
        }
    }

//for inserting list of users

    fun insertUserDataList(users: List<User>) {
        viewModelScope.launch {
            _insertUsersDataStatusList.postValue(Recurso.loading(null))
            try {
                val data = usersUseCase.addUserList(users)
                _insertUsersDataStatusList.postValue(Recurso.success(data, 0))
            } catch (exception: Exception) {
                _insertUsersDataStatusList.postValue(Recurso.error(null, exception.message!!))
            }
        }
    }


}
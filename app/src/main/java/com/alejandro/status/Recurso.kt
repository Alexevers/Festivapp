package com.alejandro.status

//Clase que hace referencia a un recurso de datos, almacena los datos devueltos y un status para saber como se ha efectuado la funcion
data class Recurso<out T>(val status: Status, val data: T?, val id: Long, val message: String?) {
    companion object {
        fun <T> success(data: T?, id: Long): Recurso<T> =
            Recurso(status = Status.SUCCESS, data = data, id = id, message = "SUPERADO")

        fun <T> error(data: T?, message: String): Recurso<T> =
            Recurso(status = Status.ERROR, data = data, message = message, id = 0L)

        fun <T> loading(data: T?): Recurso<T> =
            Recurso(status = Status.LOADING, data = data, message = null, id = 0L)
    }
}
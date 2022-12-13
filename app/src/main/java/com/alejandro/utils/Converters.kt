package com.alejandro.utils

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it *1000) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        //es necesario dividirlo entre 1000 porque nos dá milisegundos y nos interesan segundos
        return date?.time?.toLong()?.div(1000 )
    }
}
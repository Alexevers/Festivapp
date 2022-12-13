package com.alejandro.roomDB

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alejandro.classes.Festival
import com.alejandro.classes.Genero
import com.alejandro.classes.Ticket
import com.alejandro.classes.User
import com.alejandro.funcFestival.FestivalDao
import com.alejandro.funcTicket.TicketDao
import com.alejandro.funcUsuario.UserDao
import com.alejandro.utils.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@Database(entities = [User::class,Festival::class, Ticket::class], version = 8, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun festivalDao(): FestivalDao
    abstract fun ticketDao(): TicketDao
    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "festivapp_db8"
                ).addCallback(AppDatabaseCallback(scope)).allowMainThreadQueries().build()
                INSTANCE = instance

                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.userDao(),database.festivalDao())
                }
            }
        }

       @SuppressLint("SimpleDateFormat")
       fun populateDatabase(userDao: UserDao, festivalDao: FestivalDao) {

            userDao.deleteAll()
            //Add sample Users
            var user = User(0,"Dani","Rosique","1234","dani@gmail.com")
            userDao.insert(user)
            user = User(0,"Alex","Illán","1234","alex@gmail.com")
            userDao.insert(user)

           val format =SimpleDateFormat("yyyy-MM-dd")

            var festival = Festival(0,"RocanRola","Alicante", format.parse("2023-10-7") as Date,
                format.parse("2023-10-8") as Date,"Kase-o","Festival de Rap Muy Chulo", Genero.RAP)
           festivalDao.insert(festival)

           festival = Festival(0,"PirataFest","Gandía", format.parse("2023-07-13") as Date,
               format.parse("2023-07-16") as Date,"Ska-P","Festival de Rock",Genero.ROCK)
            festivalDao.insert(festival)

           festival = Festival(0,"Boombastic","Costa Del Sol", format.parse("2023-08-14") as Date,
               format.parse("2023-08-16") as Date,"Duki","Festival con Muchas Ediciones",Genero.REGGAETON)
            festivalDao.insert(festival)

           festival = Festival(0,"Boombastic","Benidorm", format.parse("2023-08-18") as Date,
               format.parse("2023-08-20") as Date,"C.Tangana","Festival con Muchas Ediciones",Genero.REGGAETON)
            festivalDao.insert(festival)

           festival = Festival(0,"Boombastic","Madrid", format.parse("2023-06-10") as Date,
               format.parse("2023-06-11") as Date,"Bizarrap","Festival con Muchas Ediciones",Genero.RAP)
            festivalDao.insert(festival)

           festival = Festival(0,"Boombastic","Asturias", format.parse("2023-07-21") as Date,
               format.parse("2023-07-23") as Date,"C.Tangana","Festival con Muchas Ediciones",Genero.REGGAETON)
            festivalDao.insert(festival)

           festival = Festival(0,"Juergas","Almeria",
               format.parse("2023-08-4") as Date,
               format.parse("2023-08-6") as Date,"Dubioza Kolektiv","Festival de Rock-Ska al que van Grupos Chulos",Genero.SKA)
            festivalDao.insert(festival)
        }
    }
}




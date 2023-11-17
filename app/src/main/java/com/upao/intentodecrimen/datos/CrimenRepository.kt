package com.upao.intentodecrimen.datos

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.upao.intentodecrimen.modelo.Crimen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID



private const val DATABASE_NAME="crimen-database"
class CrimenRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope=GlobalScope){
    private val database = Room.databaseBuilder(
        context.applicationContext,
        CrimenDatabase::class.java,
        DATABASE_NAME
    ).build()
    fun actualizarCrimen(crimen: Crimen){
        coroutineScope.launch{
            database.crimenDAO().actualizarCrimen(crimen)
        }
    }
    fun getCrimenes():Flow<List<Crimen>> = database.crimenDAO().getCrimenes()
    suspend fun getCrimen(id: UUID):Crimen = database.crimenDAO().getCrimen(id)
    suspend fun ingresarCrimen(crimen: Crimen) {
        Log.d("CrimenRepository", "Insertando crimen: $crimen")
        database.crimenDAO().ingresarCrimen(crimen)
    }

    companion object{
        private var INSTANCIA:CrimenRepository?=null
        fun inicializar(context: Context){
            if(INSTANCIA==null){
                INSTANCIA= CrimenRepository(context)
            }
        }
        fun get(): CrimenRepository{
            return INSTANCIA?:
            throw java.lang.IllegalStateException("Debe inicializar el repositorio")
        }
    }
    //se agrego:
    suspend fun eliminarCrimen(crimen: Crimen) {
        database.crimenDAO().eliminarCrimen(crimen)
    }
}
package com.upao.intentodecrimen.datos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import com.upao.intentodecrimen.modelo.Crimen
import java.util.UUID
import kotlinx.coroutines.flow.Flow

@Dao
interface CrimenDAO {
    @Query("SELECT * FROM crimen")
    fun getCrimenes(): Flow<List<Crimen>>

    @Query("SELECT * FROM crimen WHERE id=(:id)")
    suspend fun getCrimen(id:UUID):Crimen

    @Insert
    suspend fun ingresarCrimen(crimen:Crimen)

    @Update
    suspend fun actualizarCrimen(crimen:Crimen)


}
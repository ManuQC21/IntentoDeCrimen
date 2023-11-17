package com.upao.intentodecrimen.datos

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.upao.intentodecrimen.modelo.Crimen

@Database(entities = [Crimen::class], version = 1)
@TypeConverters(CrimenTypeConverter::class)
abstract class CrimenDatabase: RoomDatabase() {
    abstract fun crimenDAO():CrimenDAO
}
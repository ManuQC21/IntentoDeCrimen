package com.upao.intentodecrimen.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class Crimen(
    @PrimaryKey
    val id: UUID,
    val titulo: String,
    val fecha: Date,
    val resuelto: Boolean
)

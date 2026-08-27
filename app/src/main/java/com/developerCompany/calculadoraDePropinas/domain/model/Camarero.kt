package com.developerCompany.calculadoraDePropinas.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "camareros")
data class Camarero(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String,
    val horasTrabajadas: Double,
    var propina: Double = 0.0
) : Parcelable

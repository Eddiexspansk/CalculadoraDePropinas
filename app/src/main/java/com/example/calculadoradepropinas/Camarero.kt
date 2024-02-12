package com.example.calculadoradepropinas
import android.os.Parcel
import android.os.Parcelable


data class Camarero(val nombre: String, val horasTrabajadas: Double, var propina: Double = 0.0): Parcelable {
    // Implementación de Parcelable
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeDouble(horasTrabajadas)
        parcel.writeDouble(propina)
    }

    override fun describeContents(): Int {
        return 0
    }

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readDouble()
    )

    companion object CREATOR : Parcelable.Creator<Camarero> {
        override fun createFromParcel(parcel: Parcel): Camarero {
            return Camarero(parcel)
        }

        override fun newArray(size: Int): Array<Camarero?> {
            return arrayOfNulls(size)
        }

    }
}




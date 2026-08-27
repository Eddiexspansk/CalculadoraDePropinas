package com.developerCompany.calculadoraDePropinas.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.developerCompany.calculadoraDePropinas.domain.model.Camarero

@Database(entities = [Camarero::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun camareroDao(): CamareroDao
}

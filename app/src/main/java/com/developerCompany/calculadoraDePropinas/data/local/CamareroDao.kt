package com.developerCompany.calculadoraDePropinas.data.local

import androidx.room.*
import com.developerCompany.calculadoraDePropinas.domain.model.Camarero
import kotlinx.coroutines.flow.Flow

@Dao
interface CamareroDao {
    @Query("SELECT * FROM camareros")
    fun getAllCamareros(): Flow<List<Camarero>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCamarero(camarero: Camarero)

    @Delete
    suspend fun deleteCamarero(camarero: Camarero)

    @Query("DELETE FROM camareros")
    suspend fun deleteAll()
}

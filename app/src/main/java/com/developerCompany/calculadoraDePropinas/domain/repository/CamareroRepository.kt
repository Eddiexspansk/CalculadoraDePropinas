package com.developerCompany.calculadoraDePropinas.domain.repository

import com.developerCompany.calculadoraDePropinas.domain.model.Camarero
import kotlinx.coroutines.flow.Flow

interface CamareroRepository {
    fun getCamareros(): Flow<List<Camarero>>
    suspend fun addCamarero(camarero: Camarero)
    suspend fun removeCamarero(camarero: Camarero)
    suspend fun clearAll()
}

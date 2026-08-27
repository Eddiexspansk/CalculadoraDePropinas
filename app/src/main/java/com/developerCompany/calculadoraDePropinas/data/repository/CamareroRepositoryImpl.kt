package com.developerCompany.calculadoraDePropinas.data.repository

import com.developerCompany.calculadoraDePropinas.data.local.CamareroDao
import com.developerCompany.calculadoraDePropinas.domain.model.Camarero
import com.developerCompany.calculadoraDePropinas.domain.repository.CamareroRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CamareroRepositoryImpl @Inject constructor(
    private val dao: CamareroDao
) : CamareroRepository {
    override fun getCamareros(): Flow<List<Camarero>> = dao.getAllCamareros()

    override suspend fun addCamarero(camarero: Camarero) = dao.insertCamarero(camarero)

    override suspend fun updateCamarero(camarero: Camarero) = dao.insertCamarero(camarero)

    override suspend fun removeCamarero(camarero: Camarero) = dao.deleteCamarero(camarero)

    override suspend fun clearAll() = dao.deleteAll()
}

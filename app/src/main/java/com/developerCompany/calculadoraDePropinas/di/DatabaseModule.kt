package com.developerCompany.calculadoraDePropinas.di

import android.content.Context
import androidx.room.Room
import com.developerCompany.calculadoraDePropinas.data.local.AppDatabase
import com.developerCompany.calculadoraDePropinas.data.local.CamareroDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "propis_db"
        ).build()
    }

    @Provides
    fun provideCamareroDao(db: AppDatabase): CamareroDao {
        return db.camareroDao()
    }
}

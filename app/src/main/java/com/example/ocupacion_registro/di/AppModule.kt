package com.example.ocupacion_registro.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import com.example.ocupacion_registro.data.ocupacion.local.ocupacionDao
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ocupacionDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideOcupacionDatabase(
        @ApplicationContext context: Context
    ): ocupacionDatabase {
        return Room.databaseBuilder(
            context,
            ocupacionDatabase::class.java,
             "ocupacion_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideOcupacionDao(database: ocupacionDatabase): ocupacionDao {
        return database.ocupacionDao()
    }
}


package com.example.ocupacion_registro.data.ocupacion.local

import androidx.room.Room
import androidx.room.processor.Context
import androidx.test.espresso.core.internal.deps.dagger.Module
import androidx.test.espresso.core.internal.deps.dagger.Provides
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideOcupacionDatabase(
        @ApplicationContext context: Context
    ): OcupacionDatabase {
        return Room.databaseBuilder(
            context,
            OcupacionDatabase::class.java,
            "ocupacion_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideOcupacionDao(database: OcupacionDatabase): ocupacionDao {
        return database.OcupacionDao()
    }
}
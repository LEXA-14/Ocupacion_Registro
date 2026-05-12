package com.example.ocupacion_registro.di

import android.content.Context
import androidx.room.Room
import androidx.test.espresso.core.internal.deps.dagger.Binds
import androidx.test.espresso.core.internal.deps.dagger.Module
import androidx.test.espresso.core.internal.deps.dagger.Provides
import com.example.ocupacion_registro.data.ocupacion.local.ocupacionDao
import com.example.ocupacion_registro.data.ocupacion.local.repository.ocupacionRepositoryImp
import com.example.ocupacion_registro.domain.ocupacion.repository.OcupacionRepository
import ocupacionDatabase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

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
    fun provideTaskDao(database: ocupacionDatabase): ocupacionDao {
        return database.OcupacionDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        impl: ocupacionRepositoryImp
    ): OcupacionRepository
}
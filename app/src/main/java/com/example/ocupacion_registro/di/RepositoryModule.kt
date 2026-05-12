package com.example.ocupacion_registro.di

import androidx.test.espresso.core.internal.deps.dagger.Binds
import androidx.test.espresso.core.internal.deps.dagger.Module
import com.example.ocupacion_registro.data.ocupacion.local.repository.ocupacionRepositoryImp
import com.example.ocupacion_registro.domain.ocupacion.repository.OcupacionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        impl: ocupacionRepositoryImp
    ): OcupacionRepository
}
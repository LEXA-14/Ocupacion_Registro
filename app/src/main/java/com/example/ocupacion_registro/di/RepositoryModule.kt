package com.example.ocupacion_registro.di


import com.example.ocupacion_registro.data.empleado.local.repository.empleadoRepositoryImp
import dagger.Module
import dagger.Binds
import com.example.ocupacion_registro.data.ocupacion.local.repository.ocupacionRepositoryImp
import com.example.ocupacion_registro.domain.empleado.repository.empleadoRepository
import com.example.ocupacion_registro.domain.ocupacion.repository.OcupacionRepository
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        impl: ocupacionRepositoryImp
    ): OcupacionRepository


    @Binds
    @Singleton
    abstract fun bindEmpleadoRepository(
        impl: empleadoRepositoryImp
    ): empleadoRepository
}
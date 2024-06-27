package com.example.catapi.di
import com.example.catapi.data.datasource.remote.CatRemoteDataSource
import com.example.catapi.data.datasource.remote.CatRemoteDataSourceImpl
import com.example.catapi.data.mapper.ListMapper
import com.example.catapi.data.repository.CatRepositoryImpl
import com.example.catapi.domain.CatRepository
import com.example.catapi.domain.CatUseCase
import com.example.catapi.presentation.viewmodel.CatViewModel
import org.koin.dsl.module


val remoteDataSourceModule = module {
    factory<CatRemoteDataSource> {
        CatRemoteDataSourceImpl(
            api = get()
        )
    }
}

val mapperModule = module {
    factory { ListMapper() }
}

val repositoryModule = module {
    factory<CatRepository> {
        CatRepositoryImpl(
            remoteDataSource = get()
        )
    }
}

val useCaseModule = module {
    factory {
        CatUseCase(
            catRepository = get()
        )
    }
}

val mainModule = module {
    factory {
        CatViewModel(
            catUseCase = get()
        )
    }
}
package com.example.catapi.di

import com.example.catapi.data.api.CatApi
import com.example.catapi.data.datasource.remote.CatRemoteDataSource
import com.example.catapi.data.datasource.remote.CatRemoteDataSourceImpl
import com.example.catapi.data.mapper.ListMapper
import com.example.catapi.data.repository.CatRepositoryImpl
import com.example.catapi.domain.CatRepository
import com.example.catapi.domain.CatUseCase
import com.example.catapi.presentation.viewmodel.CatViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val okHttpClientModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
}

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<CatApi> {
        get<Retrofit>().create(CatApi::class.java)
    }
}

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
package com.example.catapi.data.retrofit

import android.content.Context
import com.example.catapi.data.api.CatApi
import com.example.catapi.data.api.ClientErrorInterceptor
import com.example.catapi.data.api.ConnectivityInterceptor
import com.example.catapi.data.api.ServerErrorInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val okHttpClientModule = module {
    single { provideConnectivityInterceptor(get()) }
    single { provideOkHttpClient(get()) }
    single { providerClientErrorInterceptor() }
    single { providerServerErrorInterceptor()}
}

internal fun provideConnectivityInterceptor(context: Context): ConnectivityInterceptor {
    return ConnectivityInterceptor(context)
}

internal fun providerClientErrorInterceptor(): ClientErrorInterceptor {
    return ClientErrorInterceptor()
}
internal fun providerServerErrorInterceptor(): ServerErrorInterceptor {
    return ServerErrorInterceptor()
}

internal fun provideOkHttpClient(connectivityInterceptor: ConnectivityInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(connectivityInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
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
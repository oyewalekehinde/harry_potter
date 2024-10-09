package com.harrypotter.app.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.harrypotter.app.data.local.room.AppDatabase
import com.harrypotter.app.data.local.room.CharacterDao
import com.harrypotter.app.data.remote.api.ApiService
import com.harrypotter.app.data.repository.CharacterRepositoryImpl
import com.harrypotter.app.domain.repository.CharacterRepository
import com.harrypotter.app.viewModels.CharactersViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

val networkModule = module{
    single {
        providesBaseClient()
    }
    single{
        providesMoshi()
    }
    single {
        provideMoshiConverterFactory(get())
    }
    single {
        providesRetrofit(get(), get())
    }
    single {
        provideService(get())
    }
}
val localModule = module {
    single {
        provideAppDatabase(get())
    }
    single {
        provideCharacterDao(get())
    }

}
val repositoryModule = module {
    single<CharacterRepository> {
        provideRepository(get(), get(), get())
    }
}
val viewModelModule = module {
    viewModel {
    CharactersViewModel(get())
    }
}

val appModule = listOf(networkModule, localModule, repositoryModule, viewModelModule)

fun providesBaseClient() : OkHttpClient.Builder{
    return  OkHttpClient.Builder()
        .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)

}
fun providesMoshi(): Moshi {
    return Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}
fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
    return MoshiConverterFactory.create(moshi)
}
fun providesRetrofit(
    okHttpClient: OkHttpClient.Builder,
    moshiConverterFactory: MoshiConverterFactory,
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callbackExecutor(Executors.newSingleThreadExecutor())
        .client(okHttpClient.build())
        .addConverterFactory(moshiConverterFactory)
        .build()
}
fun provideService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
fun provideCharacterDao(database: AppDatabase): CharacterDao {
    return database.dao()
}
fun provideAppDatabase(context: Context): AppDatabase {
    return   databaseBuilder(
        context,
        AppDatabase::class.java,
        AppDatabase.DATABASE_NAME)
        .fallbackToDestructiveMigration().build()
}
fun provideRepository(
    characterDao: CharacterDao,
    apiService: ApiService,
    context: Context
): CharacterRepository{
    return  CharacterRepositoryImpl(characterDao, apiService,context )
}

private const val NETWORK_TIMEOUT = 30L
private const val BASE_URL = "https://hp-api.onrender.com/api/"
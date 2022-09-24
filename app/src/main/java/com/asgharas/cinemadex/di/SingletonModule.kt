package com.asgharas.cinemadex.di

import android.content.Context
import androidx.room.Room
import com.asgharas.cinemadex.model.api.ApiService
import com.asgharas.cinemadex.model.db.CinemaDao
import com.asgharas.cinemadex.model.db.CinemaDb
import com.asgharas.cinemadex.model.repository.CinemaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// TODO remove logging from RELEASE build code
@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
//        val interceptor = HttpLoggingInterceptor().apply {
//            setLevel(HttpLoggingInterceptor.Level.HEADERS)
//        }
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .build()

        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
//            .client(okHttpClient)                      // will be commented out for release build
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesCinemaRepository(
        apiService: ApiService,
        cinemaDao: CinemaDao,
        @ApplicationContext context: Context
    ): CinemaRepository =
        CinemaRepository(apiService, cinemaDao)


    @Provides
    @Singleton
    fun providesCinemaDb(@ApplicationContext cxt: Context): CinemaDb {
        return Room.databaseBuilder(
            cxt,
            CinemaDb::class.java,
            "cinema_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesDao(db: CinemaDb): CinemaDao = db.getDB()

}
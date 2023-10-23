package com.sr.compose.di.module

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sr.compose.BuildConfig
import com.sr.compose.api.MovieApi
import com.sr.compose.api.NetworkConstants
import com.sr.compose.api.OAuthInterceptor
import com.sr.compose.util.LocalDateConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePrefs(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }

    @Singleton
    @Provides
    fun provideOauthInterceptor() = OAuthInterceptor()


    @Singleton
    @Provides
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        oAuthInterceptor: OAuthInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder().connectTimeout(NetworkConstants.CRW_TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(NetworkConstants.CRW_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetworkConstants.CRW_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(oAuthInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideGson(): Gson =
        GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate::class.java, LocalDateConverter())
            .create()

    @Singleton
    @Provides
    fun provideApi(gson: Gson, okHttpClient: OkHttpClient): MovieApi {
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(MovieApi::class.java)
    }
}
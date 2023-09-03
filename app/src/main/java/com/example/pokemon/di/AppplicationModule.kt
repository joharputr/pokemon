package com.example.pokemon.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pokemon.BuildConfig
import com.example.pokemon.core.API
import com.example.pokemon.database.AppDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

@InstallIn(SingletonComponent::class)
@Module
class AppplicationModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                    return arrayOf()
                }
            }
        )

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory
        val trustManagerFactory: TrustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(null as KeyStore?)
        val trustManagers: Array<TrustManager> =
            trustManagerFactory.trustManagers
        check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
            "Unexpected default trust managers:" + trustManagers.contentToString()
        }

        val trustManager = trustManagers[0] as X509TrustManager
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustManager)
            .hostnameVerifier(hostnameVerifier = { _, session -> true })
            .connectTimeout(600, TimeUnit.SECONDS)
            .readTimeout(600, TimeUnit.SECONDS)
            .writeTimeout(600, TimeUnit.SECONDS)
            .addInterceptor(logging)
//            .addInterceptor(object : Interceptor {
//                override fun intercept(chain: Interceptor.Chain): Response {
//                    Log.d("tokeeen"," = ${sessionRepository.getToken()}")
//
//                    val originalReques = chain.request()
//                    val requestBuilder = originalReques.newBuilder()
//                        .addHeader(
//                            "Authorization",
//                            " Bearer " + sessionRepository.getToken()
//                        )
//                        .method(originalReques.method, originalReques.body)
//                    val newRequest = requestBuilder.build()
//                    return chain.proceed(newRequest)
//                }
//            })

            .build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideBoolean(): Boolean = false

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application

    @Provides
    fun provideApi(@Named("Unauthorized") retrofit: Retrofit): API =
        retrofit.create(API::class.java)

    lateinit var a: Retrofit

    @Singleton
    @Provides
    @Named("Authorized")
    fun provideAuthorizedRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        try {
            a = Retrofit.Builder().baseUrl(
                BuildConfig.BASE_URL
            ).addConverterFactory(GsonConverterFactory.create(gson)).client(
                okHttpClient
            ).build()

        } catch (e: Exception) {
            a = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
        }
        return a
    }

    @Singleton
    @Provides
    @Named("Unauthorized")
    fun provideUnAuthorizedRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        try {
            a = Retrofit.Builder().baseUrl(
                BuildConfig.BASE_URL
            ).addConverterFactory(GsonConverterFactory.create(gson)).client(
                okHttpClient
            ).build()

        } catch (e: Exception) {
            a = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
        }
        return a
    }

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .allowMainThreadQueries().fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                }
            })
            .build()

    @Singleton
    @Provides
    fun provideDetailGamesDao(appDatabase: AppDatabase) = appDatabase.ListPokemonDao()


    companion object {
        private const val DATABASE_NAME = "pokemonDb"
        const val TIMEOUT = 5L
    }


}
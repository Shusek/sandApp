package sandbox.myapplication.common.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.stringBased
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.JSON
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import sandbox.myapplication.common.api.GithubApi
import javax.inject.Singleton


@Module
class NetworkModule {


    @Provides
    @Singleton
    fun providesOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        return httpClientBuilder
                .addInterceptor(interceptor)
                .build()
    }

    @Provides
    @Singleton
    fun providesInterceptor(): Interceptor {
        return Interceptor { chain ->
            chain.proceed(chain.request().newBuilder()
                    .header("Accept", "application/vnd.github.v3+json")
                    .build())
        }
    }

    @Provides
    @Singleton
    fun providesConverter(): Converter.Factory {
        val contentType = MediaType.parse("application/json")!!
        val json = JSON.nonstrict
        return stringBased(contentType, json::parse, json::stringify)
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient, converter: Converter.Factory): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(converter)
                .build()
    }

    @Provides
    @Singleton
    fun providesGithubApi(retrofit: Retrofit): GithubApi = retrofit.create(GithubApi::class.java)

}

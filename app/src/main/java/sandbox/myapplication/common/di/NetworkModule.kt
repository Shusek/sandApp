package sandbox.myapplication.common.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import sandbox.myapplication.common.api.GithubApi
import javax.inject.Singleton


@Module
class NetworkModule {


    @Provides
    @Singleton
    fun providesOkHttpClient(interceptors:List<Interceptor>): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        return httpClientBuilder
                .addInterceptors(interceptors)
                .build()
    }

    @Provides
    @Singleton
    fun providesInterceptors(): List<Interceptor> {
      return listOf( Interceptor { chain ->
            chain.proceed(chain.request().newBuilder()
                    .header("Accept", "application/vnd.github.v3+json")
                    .build())})

    }
    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }
    @Provides
    @Singleton
    fun providesGithubApi(retrofit: Retrofit): GithubApi = retrofit.create(GithubApi::class.java)

}
private fun OkHttpClient.Builder.addInterceptors(interceptors:List<Interceptor>): OkHttpClient.Builder {
    for (interceptor in interceptors) {
        addInterceptor(interceptor)
    }
    return this
}

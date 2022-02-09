package `in`.bitlogger.payme.di

import `in`.bitlogger.payme.network.BasicAuthInterceptor
import android.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    /**
     * Provides the retrofit client for pay me heroku API.
     * */
    @Provides
    @Singleton
    fun payMeClient(): Retrofit = Retrofit.Builder()
        .baseUrl("https://cash-flow-manager-api.herokuapp.com/")
        .client(authInterceptor())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun authInterceptor() = OkHttpClient.Builder()
        .addInterceptor(BasicAuthInterceptor("admin", "SuperSecretPassword"))
        .build()
}
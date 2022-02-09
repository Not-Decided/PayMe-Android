package `in`.bitlogger.payme.network

import android.R.attr
import okhttp3.*
import java.io.IOException


class BasicAuthInterceptor(user: String, password: String) :
    Interceptor {
    private val credentials: String = Credentials.basic(user, password)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authenticatedRequest: Request = request.newBuilder()
            .header("Authorization", credentials).build()
        return chain.proceed(authenticatedRequest)
    }
}
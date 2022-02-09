package `in`.bitlogger.payme.network

import `in`.bitlogger.payme.db.Model.LoginResponse
import `in`.bitlogger.payme.db.Model.Subscription
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SubscriptionService {

    @POST("subscription")
    suspend fun addSubscription(@Body sub: Subscription): Response<LoginResponse>
}
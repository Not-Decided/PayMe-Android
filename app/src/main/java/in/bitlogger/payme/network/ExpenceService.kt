package `in`.bitlogger.payme.network

import `in`.bitlogger.payme.db.Model.Expence
import `in`.bitlogger.payme.db.Model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ExpenceService {

    @POST("bill")
    suspend fun addExpence(@Body expence: Expence): Response<LoginResponse>
}
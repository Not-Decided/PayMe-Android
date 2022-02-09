package `in`.bitlogger.payme.network

import `in`.bitlogger.payme.db.Model.LoginResponse
import `in`.bitlogger.payme.db.Model.SignIn
import `in`.bitlogger.payme.db.Model.SignUp
import `in`.bitlogger.payme.db.Model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService {

    /**
     * Create the new user.
     * @return userId: It returns the user ID.
     * */
    @POST("signup")
    suspend fun createUser(@Body signUp: SignUp): Response<LoginResponse>

    /**
     * Logs in the new user.
     * @return userId: It returns the user ID.
     * */
    @POST("login")
    suspend fun sigInUser(@Body signIn: SignIn): Response<LoginResponse>
}
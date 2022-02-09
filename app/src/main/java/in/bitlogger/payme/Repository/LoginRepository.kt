package `in`.bitlogger.payme.Repository

import `in`.bitlogger.payme.db.Model.LoginResponse
import `in`.bitlogger.payme.db.Model.SignIn
import `in`.bitlogger.payme.db.Model.SignUp
import `in`.bitlogger.payme.db.Model.User
import `in`.bitlogger.payme.network.LoginService
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject

class LoginRepository @Inject constructor(
    payMeClient: Retrofit
){
    private val loginService = payMeClient.create(LoginService::class.java)

    suspend fun signUpUser(signUp: SignUp) = loginService.createUser(signUp)

    suspend fun signInUser(signIn: SignIn) = loginService.sigInUser(signIn)
}
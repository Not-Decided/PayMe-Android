package `in`.bitlogger.payme.db.Model

import java.util.*

data class LoginResponse(
    val message: String,
    val id: String?
)

data class SignIn(
    val email: String,
    val password: String
)

data class SignUp(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val dob: String,
    val gender: String,
)
package `in`.bitlogger.payme.ui.viewmodel

import `in`.bitlogger.payme.Repository.LoginRepository
import `in`.bitlogger.payme.db.Model.LoginResponse
import `in`.bitlogger.payme.db.Model.SignIn
import `in`.bitlogger.payme.db.Model.SignUp
import `in`.bitlogger.payme.db.Model.User
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    var loginRepository: LoginRepository
): ViewModel() {

    private val signInResponse: MutableLiveData<LoginResponse> = MutableLiveData()
    private val signUpResponse: MutableLiveData<LoginResponse> = MutableLiveData()

    fun signUpUser(signUp: SignUp): LiveData<LoginResponse> {
        viewModelScope.launch {
            val response = loginRepository.signUpUser(signUp)
            Timber.d("RES ${response.message()} ${response.code()} ${response.body()?.id} ${response.body()?.message}")
            signUpResponse.postValue(
                LoginResponse(
                    response.body()?.message ?: "No Response",
                    response.body()?.id
                )
            )
        }
        return signUpResponse
    }

    fun signInUser(signIn: SignIn): LiveData<LoginResponse> {
        viewModelScope.launch {
            val response = loginRepository.signInUser(signIn)
            Timber.d("RES ${response.message()} ${response.code()} ${response.body()?.id} ${response.body()?.message}")
            signInResponse.postValue(
                LoginResponse(
                    response.body()?.message ?: "No Response",
                    response.body()?.id
                )
            )
        }
        return signInResponse
    }
}
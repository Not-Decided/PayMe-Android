package `in`.bitlogger.payme.ui

import `in`.bitlogger.payme.databinding.ActivityLoginBinding
import `in`.bitlogger.payme.ui.fragment.SigninFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var sigInBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sigInBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(sigInBinding.root)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<SigninFragment>(sigInBinding.loginFragContainer.id)
        }
    }
}
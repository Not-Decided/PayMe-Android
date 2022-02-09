package `in`.bitlogger.payme.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.bitlogger.payme.R
import `in`.bitlogger.payme.databinding.FragmentSigninBinding
import `in`.bitlogger.payme.db.Model.SignIn
import `in`.bitlogger.payme.ui.viewmodel.LoginViewModel
import android.util.Patterns
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class SigninFragment : Fragment() {

    private lateinit var signinBinding: FragmentSigninBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signin, container, false)
        signinBinding = FragmentSigninBinding.bind(view)

        signinBinding.signinSignupBtn.setOnClickListener {
            parentFragmentManager.commit{
                replace<SignupFragment>(R.id.login_frag_container)
            }
        }

        signinBinding.signinBtn.setOnClickListener {
            val email = signinBinding.signinEmailEdittext.text.toString().trim()
            val pass = signinBinding.signinPasswordEdittext.text.toString().trim()
            signInUser(email, pass)
        }
        return view
    }

    private fun signInUser(email: String, password: String) {
        val emailPattern: Pattern = Patterns.EMAIL_ADDRESS
        val passPattern: Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\\\S+\$).{4,}\$")

        if (!emailPattern.matcher(email).matches() || email.isEmpty()) {
            signinBinding.signinEmail.error = "Enter Valid Mail"
            Toast.makeText(requireContext(),
                "Enter Valid Mail",
                Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isEmpty()) {
            signinBinding.signinPassword.error = "Enter Valid Password"
            Toast.makeText(requireContext(),
                "Enter Valid Password",
                Toast.LENGTH_SHORT).show()
            return
        }
        loginViewModel.signInUser(SignIn(email, password)).observe(viewLifecycleOwner, Observer {
            if (it.id == null) {
                Toast.makeText(requireContext(),
                    "${it.message}   ${it.id}",
                    Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(requireContext(),
                it.message,
                Toast.LENGTH_SHORT).show()
        })
    }
}
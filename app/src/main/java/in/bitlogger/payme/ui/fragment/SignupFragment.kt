package `in`.bitlogger.payme.ui.fragment

import `in`.bitlogger.payme.R
import `in`.bitlogger.payme.databinding.FragmentSignupBinding
import `in`.bitlogger.payme.db.Model.SignUp
import `in`.bitlogger.payme.ui.viewmodel.LoginViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.security.acl.Owner
import java.time.Instant
import java.util.*

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private lateinit var signupBinding: FragmentSignupBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)
        signupBinding = FragmentSignupBinding.bind(view)

        setGenderList()

        signupBinding.signupSigninBtn.setOnClickListener {
            parentFragmentManager.commit{
                replace<SignupFragment>(R.id.login_frag_container)
            }
        }

        signupBinding.signupDobEdittext.setOnClickListener {
            selectDate()
        }

        signupBinding.signupBtn.setOnClickListener {
            val firstName = signupBinding.signupFirstNameEdittext.text.toString().trim()
            val lastName = signupBinding.signupLastNameEdittext.text.toString().trim()
            val email = signupBinding.signupEmailEdittext.text.toString().trim()
            val pass = signupBinding.signupPasswordEdittext.text.toString().trim()
            val cPass = signupBinding.signupConfirmPasswordEdittext.text.toString().trim()
            val dob = signupBinding.signupDobEdittext.text.toString().trim()
            val gender = signupBinding.signupGenderAutocompletetv.text.toString().trim()

            if (pass != cPass) {
                Toast.makeText(requireContext(),
                    "Password don't match",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val signUp = SignUp(
                email,
                firstName,
                lastName,
                pass,
                dob,
                gender
            )
            signUpUser(signUp)
        }
        return view
    }

    private fun signUpUser(signUp: SignUp) {
        loginViewModel.signUpUser(signUp).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
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

    private fun selectDate() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build()
        datePicker.show(parentFragmentManager, "tag");
        datePicker.addOnPositiveButtonClickListener {
            signupBinding.signupDobEdittext.setText(Date(it*1000).toString())
        }
        datePicker.addOnNegativeButtonClickListener {
            Toast.makeText(requireContext(),
                "Select Date",
                Toast.LENGTH_SHORT).show()
        }
        datePicker.addOnCancelListener {
            Toast.makeText(requireContext(),
                "Select Date",
                Toast.LENGTH_SHORT).show()
        }
        datePicker.addOnDismissListener {
            Toast.makeText(requireContext(),
                "Select Date",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun setGenderList() {
        val items = listOf("Male", "Female", "Trans-Gender")
        val adapter = ArrayAdapter(requireContext(), R.layout.listitem, items)
        (signupBinding.signupGender.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
}
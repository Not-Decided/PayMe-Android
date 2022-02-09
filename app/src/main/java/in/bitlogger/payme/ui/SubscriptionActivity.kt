package `in`.bitlogger.payme.ui

import `in`.bitlogger.payme.databinding.ActivitySubscriptionBinding
import `in`.bitlogger.payme.db.Model.Subscription
import `in`.bitlogger.payme.ui.viewmodel.SubscriptionViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SubscriptionActivity : AppCompatActivity() {

    private lateinit var subscriptionBinding: ActivitySubscriptionBinding
    private val subViewModel: SubscriptionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscriptionBinding = ActivitySubscriptionBinding.inflate(layoutInflater)
        setContentView(subscriptionBinding.root)

        subscriptionBinding.subStartDateEdittext.setOnClickListener {
            selectDate(it as EditText)
        }

        subscriptionBinding.subEndDateEdittext.setOnClickListener {
            selectDate(it as EditText)
        }

        subscriptionBinding.subAdd.setOnClickListener {
            addSub()
        }
    }

    private fun addSub() {
        val id = Date().time
        var amount: Float? = null
        val message = subscriptionBinding.subMessageEdittext.text.toString()
        val subName = subscriptionBinding.subNameEdittext.text.toString()
        val subStart = subscriptionBinding.subStartDateEdittext.text.toString()
        val subEnd = subscriptionBinding.subEndDateEdittext.text.toString()

        if (subscriptionBinding.subAmountEdittext.text.isNullOrEmpty()) {
            Toast.makeText(this, "Enter Amount", Toast.LENGTH_SHORT).show()
            return
        } else {
            amount = subscriptionBinding.subAmountEdittext.text.toString().toFloat()
        }

        if (subscriptionBinding.subMessageEdittext.text.isNullOrEmpty()) {
            Toast.makeText(this, "Enter Message", Toast.LENGTH_SHORT).show()
            return
        }

        if (subscriptionBinding.subNameEdittext.text.isNullOrEmpty()) {
            Toast.makeText(this, "Enter Sub Name", Toast.LENGTH_SHORT).show()
            return
        }

        if (subscriptionBinding.subStartDateEdittext.text.isNullOrEmpty()) {
            Toast.makeText(this, "Enter Start Date", Toast.LENGTH_SHORT).show()
            return
        }

        if (subscriptionBinding.subEndDateEdittext.text.isNullOrEmpty()) {
            Toast.makeText(this, "Enter End Date", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Subscription(
            id,
            subName,
            amount,
            subEnd,
            subStart,
            message
        )

        subViewModel.addSub(data)
        finish()
    }

    private fun selectDate(editText: EditText) {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .build()
        datePicker.show(supportFragmentManager, "tag");
        datePicker.addOnPositiveButtonClickListener {
            editText.setText(Date(it*1000).toString())
        }
        datePicker.addOnNegativeButtonClickListener {
            Toast.makeText(this,
                "Select Date",
                Toast.LENGTH_SHORT).show()
        }
        datePicker.addOnCancelListener {
            Toast.makeText(this,
                "Select Date",
                Toast.LENGTH_SHORT).show()
        }
        datePicker.addOnDismissListener {
            Toast.makeText(this,
                "Select Date",
                Toast.LENGTH_SHORT).show()
        }
    }
}
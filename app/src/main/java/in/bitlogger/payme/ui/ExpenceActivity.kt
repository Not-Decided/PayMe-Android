package `in`.bitlogger.payme.ui

import `in`.bitlogger.payme.core.ExpenceType
import `in`.bitlogger.payme.databinding.ActivityExpenceBinding
import `in`.bitlogger.payme.db.Model.Expence
import `in`.bitlogger.payme.db.Model.Split
import `in`.bitlogger.payme.ui.viewmodel.ExpenceViewModel
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ExpenceActivity : AppCompatActivity() {

    private lateinit var expenceBinding: ActivityExpenceBinding
    private val expenceViewModel: ExpenceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expenceBinding = ActivityExpenceBinding.inflate(layoutInflater)
        setContentView(expenceBinding.root)

        val sheet = ContactModalSheet()

        setTags()

        expenceBinding.expenceSplitSwitch.setOnCheckedChangeListener { _, _ ->
            if (expenceBinding.expenceSplitSwitch.isChecked) {
                expenceBinding.expenceSplitSwitch.isChecked = false
                Toast.makeText(this, "Feature not enabled",Toast.LENGTH_SHORT).show()
                expenceBinding.splitBill.splitBillRoot.visibility = View.INVISIBLE
            } else {
                expenceBinding.splitBill.splitBillRoot.visibility = View.INVISIBLE
            }
        }

        expenceBinding.splitBill.splitAddMember.setOnClickListener {
            sheet.show(supportFragmentManager, "AA")
        }

        expenceBinding.addExpenceBtn.setOnClickListener {
            addExpence()
        }
    }

    private fun addExpence() {
        val id = Date().time
        var amount: Float? = null
        val message = expenceBinding.expenceMessageEdittext.text.toString()
        val tag = getSelectedChipText()
        val dateAddition = Date().time
        val expenceType = ExpenceType.ONE_TIME
        val isSplit = expenceBinding.expenceSplitSwitch.isChecked

//        val names = mutableListOf<String>()

//        expenceBinding.splitBill.splitBillMembersEdittext.addTextChangedListener { editable ->
//            val trimmed = editable.toString().trim { it <= ' ' }
//            if (trimmed.length > 1 && trimmed.endsWith(",")) {
//                names.add(trimmed)
//                Toast.makeText(this, names.toString(), Toast.LENGTH_SHORT).show()
//            }
//        }

        if (id == null) {
            Toast.makeText(this, "Enter Split member details", Toast.LENGTH_SHORT).show()
            return
        }

        if (expenceBinding.expenceAmountEdittext.text.isNullOrEmpty()) {
            Toast.makeText(this, "Enter Amount", Toast.LENGTH_SHORT).show()
            return
        } else {
            amount = expenceBinding.expenceAmountEdittext.text.toString().toFloat()
        }

        if (expenceBinding.expenceMessageEdittext.text.isNullOrEmpty()) {
            Toast.makeText(this, "Enter Message", Toast.LENGTH_SHORT).show()
            return
        }

        if (tag == null) {
            Toast.makeText(this, "Select tags", Toast.LENGTH_SHORT).show()
            return
        }

        if (isSplit) {
            Toast.makeText(this, "Enter Split member details", Toast.LENGTH_SHORT).show()
            return
        }

        val expence = if (isSplit) {
            val split = Split(
                1L,
                2L,
                listOf(115.3f, 12.5f),
                listOf("A"),
                listOf(22L, 33L),
                listOf(222L, 2221L)
            )

            Expence(
                id,
                amount,
                message,
                tag,
                dateAddition,
                expenceType,
                isSplit,
                split
            )
        } else {
            Expence(
                id,
                amount,
                message,
                tag,
                dateAddition,
                expenceType,
                isSplit,
                null
            )
        }

        expenceViewModel.addExpence(expence)
        finish()
    }

    private fun getSelectedChipText(): String? {
        val id = expenceBinding.expenseTags.checkedChipId
        return if (id == View.NO_ID) {
            null
        } else {
            findViewById<Chip>(id).text.toString()
        }
    }

    private fun setTags() {
        val tagsList = listOf("Food", "Shopping", "Travel", "Grocery")

        tagsList.forEach {
            val c: Chip = Chip(this).apply {
                this.text = it
                isClickable = true
                isCheckable = true
            }
            expenceBinding.expenseTags.addView(c)
        }
    }
}
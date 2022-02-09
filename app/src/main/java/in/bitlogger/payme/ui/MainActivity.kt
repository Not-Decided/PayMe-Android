package `in`.bitlogger.payme.ui

import `in`.bitlogger.payme.databinding.ActivityMainBinding
import `in`.bitlogger.payme.ui.viewmodel.ExpenceViewModel
import `in`.bitlogger.payme.util.Constants
import `in`.bitlogger.payme.util.PiChartUtil
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnChartValueSelectedListener {

    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var piChart: PieChart
    private val expenceViewModel: ExpenceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)

        if (!allPermissionGranted()) {
            Timber.d("Not all permission granted.")
            ActivityCompat.requestPermissions(
                this, Constants.REQUIRED_PERMISSION,
                Constants.CAMERA_PERMISSION_CODE
            )
        }
        ct()
        updateExpence()

        mainActivityBinding.addExpence.setOnClickListener {
            val add = Intent(this, ExpenceActivity::class.java)
            startActivity(add)
        }

        mainActivityBinding.mainAddSub.setOnClickListener {
            val add = Intent(this, SubscriptionActivity::class.java)
            startActivity(add)
        }
    }

    private fun ct() {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this));
        }

        val python = Python.getInstance()
        val pyObj: PyObject = python.getModule("finalrun")

        val text = "ARCHIES LIMITED\n" +
                "ARCHIES LTD PHOENIX MARKETCITY\n" +
                "S-23,IIND FLOOR, 142, VELACHERY MAIN ROAD,\n" +
                "CHENNAI-600042\n" +
                "PH:72990 36438\n" +
                "GSTIN NO: 33AAECA0726C1ZG\n" +
                "REGD.OFFICE:PLOTNO 191-F,SECTOR-4,IMT\n" +
                "MANESAR, GURUGRAM, HARYANA.\n" +
                "H.E.F.23-08-2010\n" +
                "CIN NO: L36999HR 1990PLCO41175\n" +
                "EMAIL ID: archiesđarchiesonl ine.com\n" +
                "WEBSITE: wwW.archiesonl ine.com\n" +
                "TAX INVOICE\n" +
                "\"*** *\n" +
                "--\n" +
                "No.PMC19010926\n" +
                "Date :29-11-2019\n" +
                "Time :16:22\n" +
                "HSN Description ty Rate Amount\n" +
                "CODE\n" +
                "RsP\n" +
                "Rs_P\n" +
                "399.00\n" +
                "35.00\n" +
                "12.00\n" +
                "YOU ARE THE GREATE 1 399.00\n" +
                "ST MUG-FATHER\n" +
                "PRINTED PAPER MATT 1 35.00\n" +
                "R\n" +
                "CRAFT PAPER BAG- H 1 12.00\n" +
                "ORIZONTAL\n" +
                "Total\n" +
                "446.00\n" +
                "DISCOUNT\n" +
                "ROUNDING OFF\n" +
                "11.99\n" +
                "-0.01\n" +
                "Net Value\n" +
                "434.00\n" +
                "TAXABLE AMT RATE\n" +
                "CGST\n" +
                "SGST\n" +
                "HSN\n" +
                "31.24 12\n" +
                "4802\n" +
                "4819\n" +
                "6912\n" +
                ".88\n" +
                "0.00\n" +
                "21.38\n" +
                "88\n" +
                "0.00\n" +
                "21.38\n" +
                "1\n" +
                "0.01 12%\n" +
                "356.24 12%\n" +
                "23.26\n" +
                "TOTAL\n" +
                "23.26\n" +
                "387.49\n" +
                "Rupees Four Hundred Thirty Four Only\n" +
                "Cash-Rs.434.00\n" +
                "SUBJECT T0 CHENNAI JURISDICTION\n" +
                "TIN NO: 33280461027\n" +
                "CST NO: 637381 DT 11-10-20000\n" +
                "B111 ID: 201911291623\n" +
                "Cash Received: 504.00\n" +
                "Bi11\n" +
                "Balance Retd: 70.00\n" +
                "Amount: 434.00"

        val obj = pyObj.callAttr("getOutput", text)

        Toast.makeText(this, obj.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun updateExpence() {
        expenceViewModel.getAllExpenceSum().observe(this, Observer {
            if (it == null) {
                mainActivityBinding.mainHeader.mainHeaderTotalExpence.text = "₹0"
                return@Observer
            }
            mainActivityBinding.mainHeader.mainHeaderTotalExpence.text = "₹$it"
            updatePiChart()
        })
    }

    private fun updatePiChart() {
        piChart = mainActivityBinding.mainPiChart

        piChart = PiChartUtil.applySettings(piChart)
        piChart.setOnChartValueSelectedListener(this)

        expenceViewModel.getAllTags().observe(this, Observer { tags ->
            val entries: ArrayList<PieEntry> = ArrayList()
            tags.forEach { tag ->
                expenceViewModel.getTagsExpenceSum(tag).observe(this, Observer {
                    Timber.d("AA ${it.toString()}")
                    entries.add(PieEntry(
                        it,
                        tag,
                        null
                    ))
                    PiChartUtil.setData(piChart, entries)
                })
            }
        })
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Toast.makeText(this, "AA", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected() {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constants.CAMERA_PERMISSION_CODE) {
            if (allPermissionGranted()) {
                // Permission granted do the work
            } else {
                Toast.makeText(
                    this,
                    "Camera permission is required to implement this feature",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }

        if (requestCode == Constants.CONTACT_PERMISSION_CODE) {
            if (allPermissionGranted()) {
                // permission Granted
            } else {
                Toast.makeText(
                    this,
                    "Read Contacts permission is required",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun allPermissionGranted(): Boolean = Constants.REQUIRED_PERMISSION.all {
        ContextCompat.checkSelfPermission(baseContext, it) ==
                PackageManager.PERMISSION_GRANTED
    }
}
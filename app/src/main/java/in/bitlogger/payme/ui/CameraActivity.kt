package `in`.bitlogger.payme.ui

import `in`.bitlogger.payme.util.Constants
import `in`.bitlogger.payme.R
import `in`.bitlogger.payme.databinding.ActivityCameraBinding
import `in`.bitlogger.payme.ocr.TextRecognitionCustom
import android.content.ActivityNotFoundException
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import timber.log.Timber

class CameraActivity : AppCompatActivity() {

    private lateinit var cameraBinding: ActivityCameraBinding
    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_camera)

        if (allPermissionGranted()) {
            Timber.d("All permission is granted.")
            startCamera()
        } else {
            Timber.d("Not all permission granted.")
            ActivityCompat.requestPermissions(
                this, Constants.REQUIRED_PERMISSION,
                Constants.CAMERA_PERMISSION_CODE
            )
        }
    }

    private fun startCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
            Timber.d(e.message)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            TextRecognitionCustom.parseImage(clipboardManager, this, imageBitmap)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constants.CAMERA_PERMISSION_CODE) {
            if (allPermissionGranted()) {
                // Permission granted do the work
                startCamera()
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
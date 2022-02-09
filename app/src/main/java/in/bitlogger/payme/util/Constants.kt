package `in`.bitlogger.payme.util

import android.Manifest

object Constants {
    const val PAYME_DATABASE="payme_db"
    const val CAMERA_PERMISSION_CODE=10
    const val CONTACT_PERMISSION_CODE=11
    val REQUIRED_PERMISSION= arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS)
}
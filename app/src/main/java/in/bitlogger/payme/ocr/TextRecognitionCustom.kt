package `in`.bitlogger.payme.ocr

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.widget.TextView
import android.widget.Toast
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import timber.log.Timber

class TextRecognitionCustom {
    companion object{
        private val recognizer = TextRecognition
            .getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        fun parseImage(clipboardManager: ClipboardManager, context: Context, image: Bitmap) {
            val image = InputImage.fromBitmap(image, 0)
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    Timber.d("Vision Text ${visionText.text}")
                    val clipData = ClipData.newPlainText("text", visionText.text)
                    clipboardManager.setPrimaryClip(clipData)
                    Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_LONG).show()
                    // Task completed successfully
                    // ...
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    // ...
                    Timber.e(e.message)
                }
        }
    }
}
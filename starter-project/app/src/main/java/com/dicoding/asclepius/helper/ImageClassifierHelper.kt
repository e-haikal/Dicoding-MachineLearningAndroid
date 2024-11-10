package com.dicoding.asclepius.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.SystemClock
import android.util.Log
import com.dicoding.asclepius.R
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.lang.IllegalStateException

// Helper class for setting up and managing TensorFlow Lite image classification
class ImageClassifierHelper(
    var threshold: Float = 0.1f,  // Minimum confidence threshold for results
    var maxResult: Int = 1,  // Maximum number of classification results
    val modelName: String = "cancer_classification.tflite",  // Model filename
    val context: Context,
    val classifierListener: ClassifierListener?  // Listener for result callbacks
) {
    private var imageClassifier: ImageClassifier? = null  // ImageClassifier instance

    init {
        setupImageClassifier()  // Initialize the image classifier when the object is created
    }

    // Sets up the Image Classifier with model options
    private fun setupImageClassifier() {
        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)  // Set confidence threshold
            .setMaxResults(maxResult)  // Set max number of results
        val baseOptionsBuilder = BaseOptions.builder()
            .setNumThreads(4)  // Set number of threads for model inference

        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        try {
            imageClassifier = ImageClassifier.createFromFileAndOptions(
                context,
                modelName,
                optionsBuilder.build()
            )
        } catch (e: IllegalStateException) {
            // Notify listener of an error if classifier setup fails
            classifierListener?.onError(context.getString(R.string.image_classifier_failed))
            Log.e(TAG, e.message.toString())
        }
    }

    // Classifies a static image from a Uri
    fun classifyStaticImage(imageUri: Uri) {
        if (imageClassifier == null) {
            setupImageClassifier()  // Re-initialize classifier if null
        }

        // Preprocess the image to match model input requirements
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))  // Resize to model input size
            .add(CastOp(DataType.UINT8))  // Convert image data type
            .build()
        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(toBitmap(imageUri)))

        val imageProcessingOptions = ImageProcessingOptions.builder().build()

        // Measure and log the time taken for inference
        var inferenceTime = SystemClock.uptimeMillis()
        val results = imageClassifier?.classify(tensorImage, imageProcessingOptions)
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime

        // Pass results and inference time to listener
        classifierListener?.onResult(
            results,
            inferenceTime
        )
    }

    // Converts Uri to Bitmap for image processing
    private fun toBitmap(imageUri: Uri): Bitmap {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(imageUri)
        return BitmapFactory.decodeStream(inputStream)  // Decode stream to Bitmap
    }

    // Interface for handling classifier results and errors
    interface ClassifierListener {
        fun onError(error: String)  // Callback for errors
        fun onResult(
            results: List<Classifications>?,  // Callback for successful classification results
            inferenceTime: Long  // Time taken for classification
        )
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"  // Log tag for debugging
    }
}

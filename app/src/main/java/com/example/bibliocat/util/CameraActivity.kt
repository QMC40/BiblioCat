package com.example.bibliocat.util

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.util.DisplayMetrics
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bibliocat.R
import com.example.bibliocat.view.EditBookActivity
import java.io.ByteArrayOutputStream

class CameraActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        imageView = findViewById(R.id.imageView)
        // Check for camera permission and request it if not granted yet
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                100
            )
        } else {
            // Permission has already been granted so open the camera directly
            openCamera()
        }
    }

    private fun openCamera() {
        // Create an intent to open the camera and set up to receive the image back
        val takePictureIntent = Intent(ACTION_IMAGE_CAPTURE)
        // Check if there is a camera app to handle the intent
        try {
            // Start the camera activity and wait for the result if there is a camera app
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        // If there is no camera app, catch the exception and display an error message to the user
        } catch (e: ActivityNotFoundException) {
            // Display error state to the user
            Log.d("com.example.bibliocat.util.CameraActivity", "No camera app found")
            Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Check if the request to open the camera was successful and the image was captured and returned
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Get the image data from the intent and display it in the image view on the screen
            val imageBitmap = data?.extras?.get("data") as Bitmap
            // Process the image to a smaller size for display and storage
            val processedImage = processImage(imageBitmap)
            // Display the processed image in the image view
            imageView.setImageBitmap(processedImage)

            // Convert the processed image to a byte array for storage in the database
            val byteArrayOutputStream = ByteArrayOutputStream()
            // Compress the image to a PNG format with full quality
            processedImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            // Convert the compressed image to a byte array
            val byteArray = byteArrayOutputStream.toByteArray()
            // open a new book edit activity with the image data
            val intent = Intent(this, EditBookActivity::class.java)
            intent.putExtra("image", byteArray)
            startActivity(intent)
        }
    }

    private fun processImage(bitmap: Bitmap): Bitmap {
        // Get the display metrics of the device
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        // Set the desired image size for display
        val imageSize = (120 * displayMetrics.density).toInt()
        // Scale the image to the desired size
        return Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, true)
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Check if the camera permission was granted and open the camera if it was
        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            Log.d("com.example.bibliocat.util.CameraActivity", "Permission denied")
        }
    }
}
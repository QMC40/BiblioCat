package com.example.bibliocat.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

// This class is used to convert images to strings and vice versa to store them in the database
class ConvertImage {

    // This companion object has two functions, one to convert a bitmap to a string and the other
    // to convert a string to a bitmap
    companion object{

        // This function converts a bitmap to a string
        fun convertToString(bitmap: Bitmap) : String?{

            // ByteArrayOutputStream is used to write the image to a byte array
            val stream = ByteArrayOutputStream()
            // The image is compressed to a PNG format with a quality of 100
            val resultCompress = bitmap.compress(Bitmap.CompressFormat.PNG,100,stream)

            // If the image is compressed successfully, the byte array is converted to a string
            if (resultCompress){
                // The byte array is converted to a string using Base64 encoding
                val byteArray = stream.toByteArray()
                // If the image size is greater than 2MB, the image is resized to 10% of its
                // original size
                val imageAsString = if (byteArray.size > 2000000){
                    resizeImage(bitmap,0.1)
                // If the image size is between 1MB and 2MB, the image is resized to 50% of its
                // original size
                }else if (byteArray.size in 1000000..2000000){
                    resizeImage(bitmap,0.5)
                // If the image size is less than 1MB, the image is converted to a string without
                // resizing
                }else{
                    Base64.encodeToString(byteArray,Base64.DEFAULT)
                }

                // The string is returned
                return imageAsString

            } else {
                return null
            }
        }

        // This function resizes the image to a certain coefficient of its original size
        private fun resizeImage(bitmap: Bitmap, coefficient:Double) : String?{

            // The image is resized using the createScaledBitmap function from the Bitmap class
            val resizedBitmap = Bitmap.createScaledBitmap(bitmap,(bitmap.width * coefficient).toInt(),(bitmap.height * coefficient).toInt(),true)
            // The resized image is written to a byte array
            val newStream = ByteArrayOutputStream()
            // The resized image is compressed to a PNG format with a quality of 100
            val resultCompress = resizedBitmap.compress(Bitmap.CompressFormat.PNG,100,newStream)
            // The resized image is converted to a string using Base64 encoding and returned if the
            // compression is successful, otherwise null is returned
            return if (resultCompress){
                val newByteArray = newStream.toByteArray()
                Base64.encodeToString(newByteArray,Base64.DEFAULT)
            }else{
                null
            }
        }

        // This function converts a string to a bitmap
        fun convertToBitmap(imageAsString: String): Bitmap {

            // The string is decoded to a byte array
            val byteArrayAsDecodedString = Base64.decode(imageAsString, Base64.DEFAULT)
            // The byte array is converted to a bitmap and returned
            return BitmapFactory.decodeByteArray(
                byteArrayAsDecodedString,
                0,
                byteArrayAsDecodedString.size
            )
        }
    }
}
package com.example.bibliocat.util

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.bibliocat.R

/**
 * A simple [Fragment] subclass.
 * Use the [PhotoSelectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhotoSelectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_photo_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.takePhotoBtn).setOnClickListener {
            // Implement taking photo
            takePhoto()
        }

        view.findViewById<Button>(R.id.selectPhotoBtn).setOnClickListener {
            // Implement selecting photo from device
        }
    }

    private fun takePhoto() {
    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    try {
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    } catch (e: ActivityNotFoundException) {
        // display error state to the user
    }
}

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }
}
package com.example.bibliocat.util

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.bibliocat.R

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
            Log.d("taking photo", "going to take photo")
        }

        view.findViewById<Button>(R.id.selectPhotoBtn).setOnClickListener {
            // Implement selecting photo from device
        }
    }
}
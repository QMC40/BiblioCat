package com.example.bibliocat.view

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.bibliocat.R
import com.example.bibliocat.databinding.ActivityEditBookBinding
import com.example.bibliocat.model.Book
import com.example.bibliocat.util.ConvertImage
import com.example.bibliocat.viewmodel.BookViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditBookActivity : AppCompatActivity() {

    // Declare variables for the binding and the ViewModel
    private lateinit var editBookBinding: ActivityEditBookBinding
    private lateinit var bookViewModel: BookViewModel
    private lateinit var activityResultLauncherForSelectImage: ActivityResultLauncher<Intent>
    private lateinit var coverImage: Bitmap
    private var coverImageAsString: String = ""
    private var book: Book? = null

    // Initialize the bookId to -1, which is an invalid id
    private var bookId = -1

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editBookBinding = ActivityEditBookBinding.inflate(layoutInflater)
        setContentView(editBookBinding.root)

        // Get the bookId from the calling intent
        bookId = intent.getIntExtra("bookId", -1)

        // get bookviewmodel instance
        bookViewModel = ViewModelProvider(this)[BookViewModel::class.java]

        // Get the data for the book with the given bookId
        getAndSetData(bookId)

        // Set the onClickListener for the save button
        editBookBinding.saveButton.setOnClickListener {

            // Check if the title and author fields are empty, prevent saving if they are and
            // display a toast message to the user
            val nogo =
                editBookBinding.titleEditText.text.isEmpty()
                        || editBookBinding.authorEditText.text.isEmpty()

            if (nogo) {
                Toast.makeText(
                    this@EditBookActivity,
                    "Title and Author fields cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Save the book to the database in a coroutine
            lifecycleScope.launch(Dispatchers.IO) {
                // convert the cover image to a string to store in the database
                coverImage = editBookBinding.coverImageView.drawable.toBitmap()
                coverImageAsString = ConvertImage.convertToString(coverImage).toString()

                // Create a new book object with the values from the form
                book = Book(
                    title = editBookBinding.titleEditText.text.toString(),
                    author = editBookBinding.authorEditText.text.toString(),
                    genre = editBookBinding.genreEditText.text.toString(),
                    isbn = editBookBinding.isbnEditText.text.toString(),
                    publisher = editBookBinding.publisherEditText.text.toString(),
                    edition = editBookBinding.editionEditText.text.toString(),
                    pages = editBookBinding.pagesEditText.text.toString(),
                    year = editBookBinding.yearEditText.text.toString(),
                    price = editBookBinding.priceEditText.text.toString(),
                    rating = editBookBinding.ratingBar.rating.toDouble(),
                    read = editBookBinding.readSwitch.isChecked,
                    wishlist = editBookBinding.wishlistSwitch.isChecked,
                    coverImageAsString = coverImageAsString
                )

                // Set the message to display to the user depending on whether editing or adding a
                // book
                val msg = if (bookId != -1) "Book updated" else "Book added"

                // If the user is editing a book, update the book in the database
                if (bookId != -1) {
                    book!!.bookId = bookId
                    bookViewModel.update(book!!)
                } else {
                    // If the user is adding a new book, add the book to the database
                    bookViewModel.insert(book!!)
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@EditBookActivity, msg, Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        }

        // Set the onClickListener for the back button
        editBookBinding.backButton.setOnClickListener {
            finish()
        }

        // set the onClickListener for the cover image so the user can click on the image to change
        // it by either taking a photo or selecting a photo from the device gallery
        editBookBinding.coverImageView.setOnClickListener {

            // Create a dialog to allow the user to select a photo from the device or take a photo
            val dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_photo_options, null)

            val alertDialog = AlertDialog.Builder(this)
                .setView(dialogView)
                .create()

            // Set the onClickListeners for the buttons in the dialog
            dialogView.findViewById<Button>(R.id.selectPhotoBtn).setOnClickListener {
                // Implement selecting photo from device
                alertDialog.dismiss()

                // access the images
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                //startActivityForResult -> Before API 30
                activityResultLauncherForSelectImage.launch(intent)

            }

            dialogView.findViewById<Button>(R.id.takePhotoBtn).setOnClickListener {

                Log.d("EditBookActivity", "Take Photo Button Clicked")
                // Implement taking photo
                alertDialog.dismiss()

                // Create an intent to open the camera and set up to receive the image back
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                // Check if there is a camera app to handle the intent
                try {
                    // Start the camera activity and wait for the result if there is a camera app
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    // If there is no camera app, catch the exception and display an error message to the user
                } catch (e: ActivityNotFoundException) {
                    // Display error state to the user
                    Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

//            dialogView.findViewById<Button>(R.id.searchInternetBtn).setOnClickListener {
//                // Implement searching the internet for a photo
//                alertDialog.dismiss()
//            }

            dialogView.findViewById<Button>(R.id.backBtn).setOnClickListener {
                alertDialog.dismiss()
                finish()
            }

            alertDialog.show()
        }

        // Set the onClickListener for the genre TextView to allow selection of genre from the list
        editBookBinding.genreEditText.setOnClickListener {
            val dialogView =
                LayoutInflater.from(this).inflate(R.layout.dialog_genre_select, null)

            // Create a Spinner to allow the user to select a genre
            val genreDialogSpinner = dialogView.findViewById<Spinner>(R.id.dialogGenreSpinner)

            // Create an ArrayAdapter to populate the Spinner with the genre list from the resources
            // and set the adapter for the Spinner with the genre list
            val adapter = ArrayAdapter.createFromResource(
                this,
                R.array.genre_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                genreDialogSpinner.adapter = adapter
            }

            // Find the index of the current genre in the Spinner's adapter
            val currentGenre = editBookBinding.genreEditText.text.toString()
            val genreIndex = adapter.getPosition(currentGenre)

            // Set the selected item of the Spinner to the current genre
            genreDialogSpinner.setSelection(genreIndex)

            // Create an AlertDialog to display the genre selection dialog
            val genreDialog = AlertDialog.Builder(this)
                .setTitle("Select Genre")
                .setView(dialogView)
                .create()

            // set a flag to determine if the first selection is the default value to avoid missing
            // the first selection when the dialog is opened
            var isFirstSelection = true

            // Set the onItemSelectedListener for the Spinner to update the genre in the form when
            // a genre is selected
            genreDialogSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        if (isFirstSelection) {
                            isFirstSelection = false
                        } else {
                            val genre = parent.getItemAtPosition(position).toString()
                            updateGenre(genre)
                            genreDialog.dismiss()
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Do nothing
                    }
                }

            genreDialog.show()
        }
    }

    // Set the details of the book in the form fields when the activity is created, either the
    // details of the book with the given bookId or the default values for a new book if the bookId
    // is -1 and the user is adding a new book
    private fun getAndSetData(bookId: Int) {

        // Get the book details in a coroutine to avoid blocking the main thread
        CoroutineScope(Dispatchers.IO).launch {
            if (bookId != -1) {

                val book = bookViewModel.getBookById(bookId)

                withContext(Dispatchers.Main) {
                    editBookBinding.titleEditText.setText(book.title)
                    editBookBinding.authorEditText.setText(book.author)
                    editBookBinding.isbnEditText.setText(book.isbn)
                    editBookBinding.genreEditText.setText(book.genre)
                    editBookBinding.publisherEditText.setText(book.publisher)
                    editBookBinding.editionEditText.setText(book.edition)
                    editBookBinding.pagesEditText.setText(book.pages)
                    editBookBinding.yearEditText.setText(book.year)
                    editBookBinding.priceEditText.setText(book.price)
                    editBookBinding.ratingBar.rating = book.rating.toFloat()
                    editBookBinding.readSwitch.isChecked = book.read
                    editBookBinding.wishlistSwitch.isChecked = book.wishlist

                    // pull the cover image from the database as a string and convert it to a bitmap
                    // then set the bitmap as the cover image
                    coverImageAsString = book.coverImageAsString
                    val coverAsBitmap = ConvertImage.convertToBitmap(coverImageAsString)

                    editBookBinding.coverImageView.setImageBitmap(coverAsBitmap)
                }

            } else {
                // If the user is adding a new book, set the default values for the form
                editBookBinding.titleEditText.setText("")
                editBookBinding.authorEditText.setText("")
                editBookBinding.isbnEditText.setText("")
                editBookBinding.genreEditText.setText("")
                editBookBinding.publisherEditText.setText("")
                editBookBinding.editionEditText.setText("")
                editBookBinding.pagesEditText.setText("")
                editBookBinding.yearEditText.setText("")
                editBookBinding.priceEditText.setText("")
                editBookBinding.ratingBar.rating = 0.0f
                editBookBinding.readSwitch.isChecked = false
                editBookBinding.wishlistSwitch.isChecked = false
                editBookBinding.coverImageView.setImageResource(R.drawable.madkatcvr)
            }
        }
    }

    // method to update genre text using the spinner
    fun updateGenre(genre: String) {
        editBookBinding.genreEditText.setText(genre)
    }

    // method to update the cover image using the camera
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check if the request code is for the camera
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Get the image from the data intent
            val imageBitmap = data?.extras?.get("data") as Bitmap
            // Set the image to the ImageView
            editBookBinding.coverImageView.setImageBitmap(imageBitmap)
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }
}

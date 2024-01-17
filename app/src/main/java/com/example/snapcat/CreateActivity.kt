package com.example.snapcat

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.UUID


class CreateActivity : AppCompatActivity() {
    val storage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageRef = storage.getReference()

    lateinit var imageView: ImageView
    lateinit var filePath: Uri
    val PICK_IMAGE_REQUEST = 22
    lateinit var choose: Button
    lateinit var upload: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        choose = findViewById(R.id.chooseButton)
        upload = findViewById(R.id.uploadButton)
        imageView = findViewById(R.id.postImage)


        choose.setOnClickListener{
            selectImage()
        }


        upload.setOnClickListener{
            uploadImage()
        }
    }



    fun selectImage(){
        // Defining Implicit Intent to mobile gallery
        // Defining Implicit Intent to mobile gallery
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Select Image from here..."
            ),
            PICK_IMAGE_REQUEST
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {

            // Get the Uri of data
            filePath = data.data!!
            try {

                // Setting image on image view using Bitmap
                val bitmap = MediaStore.Images.Media
                    .getBitmap(
                        contentResolver,
                        filePath
                    )
                imageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                // Log the exception
                e.printStackTrace()
            }
        }
    }


    fun uploadImage(){
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            // Defining the child of storageReference
            val ref: StorageReference = storageRef
                .child(
                    "images/"
                            + UUID.randomUUID().toString()
                )

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                .addOnSuccessListener { // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            this@CreateActivity,
                            "Image Uploaded!!",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
                .addOnFailureListener { e -> // Error, Image not uploaded
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            this@CreateActivity,
                            "Failed " + e.message,
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
                .addOnProgressListener { taskSnapshot ->

                    // Progress Listener for loading
                    // percentage on the dialog box
                    val progress = (100.0
                            * taskSnapshot.bytesTransferred
                            / taskSnapshot.totalByteCount)
                    progressDialog.setMessage(
                        "Uploaded "
                                + progress.toInt() + "%"
                    )
                }
        }
    }



    fun uploadImg(view: View){
        val catImageRef = storageRef.child("")
        val catImagesRef = storageRef.child("images/")
        catImageRef.name == catImagesRef.name
        //storageRef.putBytes(catImagesRef)
    }
}
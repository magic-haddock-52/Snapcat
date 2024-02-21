package com.example.snapcat

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.UUID


import com.squareup.picasso.Picasso




class CreateActivity : AppCompatActivity() {
    val storage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageRef = storage.getReference()

    val database = Firebase.database
    val databaseRef = database.getReference("images")


    lateinit var filePath: Uri
    lateinit var webPath: Uri

    val REQUEST_IMAGE_CAPTURE = 1
    val PICK_IMAGE_REQUEST = 22

    private lateinit var chooseButton: Button
    private lateinit var cameraButton: Button
    private lateinit var uploadButton: Button
    private lateinit var imageView: ImageView




    //
    // "https://images.ctfassets.net/1wryd5vd9xez/3PKd8raHKk0RiNgiVA0bZZ/a38de39f4fc7aa302eb83d4e0e7881fd/thecorner_heroimage_in-app-payments-sdk_v2_20190416.png?w=2916&h=800&q=100&fm=png"
    //


    /*
    //        databaseRef.child("")
    //        var testString: String = databaseRef.child( "14095e45-802a-40d4-bf48-8d9ba0feb814").child("uri").toString()
    //        //testString goes into .load()
    //
    //        Picasso.get()
    //            .load("https://images.ctfassets.net/1wryd5vd9xez/3PKd8raHKk0RiNgiVA0bZZ/a38de39f4fc7aa302eb83d4e0e7881fd/thecorner_heroimage_in-app-payments-sdk_v2_20190416.png?w=2916&h=800&q=100&fm=png")
    //            .into(imageView)
    */



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        chooseButton = findViewById(R.id.chooseButton)
        cameraButton = findViewById(R.id.cameraButton)
        uploadButton = findViewById(R.id.uploadButton)
        imageView = findViewById(R.id.postImage)


        chooseButton.setOnClickListener{
            selectImage()
        }

        cameraButton.setOnClickListener {
            openCamera()
        }

        uploadButton.setOnClickListener{
            uploadImage()
        }
    }



    fun selectImage(){
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


     fun openCamera() {
         val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
         startActivityForResult(
            intent,
            REQUEST_IMAGE_CAPTURE
         )
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST &&
            resultCode == RESULT_OK && data != null && data.data != null) {

            filePath = data.data!!
            try {
                val bitmap = MediaStore.Images.Media
                    .getBitmap(contentResolver, filePath)
                imageView.setImageBitmap(bitmap)
                uploadButton.visibility = View.VISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } else if (requestCode == REQUEST_IMAGE_CAPTURE &&
            resultCode == RESULT_OK) {
            try {
                val bitmap = data?.extras?.get("data") as Bitmap
                imageView.setImageBitmap(bitmap)
                uploadButton.visibility = View.VISIBLE
            } catch (e: IOException) {
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
                .child("images/" + UUID.randomUUID().toString())
                //need to change this to an incremental string (number)


            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                .addOnSuccessListener { // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss()
                    Toast.makeText(
                            this@CreateActivity,
                            "Image Uploaded!!",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                .addOnFailureListener { e -> // Error, Image not uploaded
                    progressDialog.dismiss()
                    Toast.makeText(
                            this@CreateActivity,
                            "Failed " + e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    // Progress Listener for loading... percentage on the dialog box
                    val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%"
                    )
                }
        }
    }
}




















/*
   val storage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageRef = storage.getReference()

    val database = Firebase.database
    val myRef = database.getReference("images")

    lateinit var imageView: ImageView
    lateinit var filePath: Uri
    lateinit var webPath: Uri
    val PICK_IMAGE_REQUEST = 22
    lateinit var choose: Button
    lateinit var upload: Button
    lateinit var test: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        choose = findViewById(R.id.chooseButton)
        upload = findViewById(R.id.uploadButton)
        test = findViewById(R.id.testButton)
        imageView = findViewById(R.id.postImage)


        choose.setOnClickListener{
            selectImage()
        }


        upload.setOnClickListener{
            uploadImage()
        }

        test.setOnClickListener {
            getImage()
        }


        myRef.child("")


        var testString: String
        = myRef.child( "14095e45-802a-40d4-bf48-8d9ba0feb814")
            .child("uri").toString()
        Picasso.get()
            .load(testString)
            .into(imageView)
        //"https://images.ctfassets.net/1wryd5vd9xez/3PKd8raHKk0RiNgiVA0bZZ/a38de39f4fc7aa302eb83d4e0e7881fd/thecorner_heroimage_in-app-payments-sdk_v2_20190416.png?w=2916&h=800&q=100&fm=png"
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

    fun openCamera() {
         val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
         startActivityForResult(
            intent,
            REQUEST_IMAGE_CAPTURE
        )
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
                            + "abj"//UUID.randomUUID().toString()          //need to change this to an incremental string (number)
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


    fun getImage(){
        val ref: StorageReference = storageRef
            .child(
                "images/"
                        + "abh"
            )
        ref.getFile(filePath)
            .addOnSuccessListener {
                // Get the Uri of data
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
            .addOnFailureListener {
            }
            .addOnProgressListener {
            }
    }

    fun newImageTest() {
        var i = 100
        var j = 0

        while (i != j) {
            val ref: StorageReference = storageRef
                .child(
                    "images/"
                            + j.toString()
                )

            ref.getFile(filePath)
                .addOnSuccessListener {
                }
                .addOnFailureListener {
                }
                .addOnProgressListener {
                }
            j++
        }
    }


    fun uploadUpdatedImage(){
        if (webPath != null) {

            // Code for showing progressDialog while uploading
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            // Defining the child of storageReference
            val ref: StorageReference = storageRef
                .child(
                    "images/"
                            + UUID.randomUUID().toString()          //need to change this to an incremental string (number)
                )

            // adding listeners on upload
            // or failure of image
            ref.putFile(webPath)
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
*/
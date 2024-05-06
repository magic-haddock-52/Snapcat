package com.example.snapcat

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.UUID

class CreateActivity : AppCompatActivity() {
    val storage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageRef = storage.getReference()

    val database = FirebaseDatabase.getInstance()
    val databaseRef = database.getReference("images")


    lateinit var filePath: Uri
    lateinit var webPath: Uri

    val REQUEST_IMAGE_CAPTURE = 1
    val PICK_IMAGE_REQUEST = 22

    private lateinit var chooseButton: Button
    private lateinit var cameraButton: Button
    private lateinit var uploadButton: Button
    private lateinit var imageView: ImageView
    private lateinit var postText: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        chooseButton = findViewById(R.id.chooseButton)
        cameraButton = findViewById(R.id.cameraButton)
        uploadButton = findViewById(R.id.uploadButton)
        imageView = findViewById(R.id.postImage)
        postText = findViewById(R.id.editText)

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
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val randomUID = UUID.randomUUID().toString()
            val ref: StorageReference = storageRef
                .child("images/" + randomUID)


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

            database.reference.child("images").child(randomUID).child("uid").setValue("gs://snapcat-63f5b.appspot.com/images/" + randomUID)
            database.reference.child("images").child(randomUID).child("info").setValue(postText.text)
        }
    }
}




















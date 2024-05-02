package com.example.snapcat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage


class MainActivity : AppCompatActivity() {
    val storage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageRef = storage.reference

    val database = Firebase.database
    val databaseRef = database.getReference("images")

    var imageData = ArrayList<Int>()
    var imageDescription = ArrayList<String>()
    var data = ArrayList<ItemsViewModel>()

    lateinit var auth: FirebaseAuth


//    lateinit var data: ArrayList<ItemsViewModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        auth = Firebase.auth
        val currentUser = auth.currentUser
        auth.signInAnonymously()


       // getRelationalDatabase()
        getRecyclerData()

        val adapter = CustomAdapter(data)
        recyclerView.adapter = adapter

    }




    fun getRecyclerData(){

        imageData.add(R.drawable.img_0090)
        imageData.add(R.drawable.img_0091)
        imageData.add(R.drawable.img_0092)

        imageDescription.add("The cat's asleep in ISER.")
        imageDescription.add("The cat is vibing on a radiator.")
        imageDescription.add("Pebbles is asleep on a blue chair.")

        for (i in 1..3) {
            data.add(ItemsViewModel(imageData[i - 1], imageDescription[i - 1]))
        }
    }


    fun onPopupClick(view: View) {
        // inflate the layout of the popup window
        // inflate the layout of the popup window
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val popupView: View = inflater.inflate(R.layout.popup_window, null)
        val popupView: View = inflater.inflate(R.layout.popup_window, null)

        // create the popup window

        // create the popup window
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true // lets taps outside the popup also dismiss it

        val popupWindow = PopupWindow(popupView, width, height, focusable)



        /*
        val listener= View.OnClickListener { view ->
            when (view.getId()) {
                R.id. -> {
                    // Do some work here
                }
            }
        }
        */

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        // dismiss the popup window when touched

        // dismiss the popup window when touched
        popupView.setOnTouchListener { v, event ->
            popupWindow.dismiss()
            true
        }
    }









    /*
    fun getRelationalDatabase(){
        val imageUri: String = ""
        val imageText: String = ""
        //need to iterate through the relational database, then pass

        for (i in 1..3) {
            val imageUri = databaseRef.child(i.toString()).child("uri").get().result
            val imageText = databaseRef.child(i.toString()).child("text").get().result
            imageData.add(imageUri.toString())
            imageDescription.add(imageText.toString())
            data.add(ItemsViewModel(imageUri.toString(), imageText.toString()))
            Log.d("TAG", imageText.toString())
            /*
            databaseRef.child("images").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //This will loop through all items. Add variables to arrays or lists as required
                var i = 0
                for (snap in dataSnapshot.children) {
                    val imageUri = dataSnapshot.child(i.toString()).child("uri").value.toString()
                    val imageText = dataSnapshot.child(i.toString()).child("text").value.toString()
                    imageData.add(imageUri)
                    imageDescription.add(imageText)
                    data.add(ItemsViewModel(imageUri, imageText))
                    i++
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })*/
        }
    }
    */



    fun createPostScreen(view: View){
        val load = Intent(this, CreateActivity::class.java)
        startActivity(load)
    }
}














/*
    val storage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageRef = storage.reference

    val database = Firebase.database
    val myRef = database.getReference("images")

    lateinit var data: ArrayList<ItemsViewModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        val imageData = ArrayList<Int>()
        imageData.add(R.drawable.img_0090)
        imageData.add(R.drawable.img_0091)
        imageData.add(R.drawable.img_0092)
        imageData.add(R.drawable.img_0093)
        imageData.add(R.drawable.img_0094)
        imageData.add(R.drawable.img_0095)
        imageData.add(R.drawable.img_0096)
        imageData.add(R.drawable.img_0097)
        imageData.add(R.drawable.img_0098)
        imageData.add(R.drawable.img_0101)
        imageData.add(R.drawable.img_1530)
        imageData.add(R.drawable.img_1531)
        imageData.add(R.drawable.img_5854)
        imageData.add(R.drawable.img_5900)

        //gs://snapcat-63f5b.appspot.com/images
        /*
        1.) Save any images with a number on it (still same issue - how to track*)

        * try { save as img-1, img-2, etc. }


        2.) try { download image i, i++ }

        This will take a while, but heck it


        Issue: the whole image int thing (why not Bitmap?)

        */

        // the image with the count of view
        for (i in 1..14) {
            data.add(ItemsViewModel(imageData[i - 1], i.toString()))
            //data.add(ItemsViewModel(imageData[i - 1]))
        }

        //loadImages()

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

    }

    fun loadPictureFiles(){
       val testReference = storageRef.child("images/IMG_0090.jpeg")
        val gsReference = storage.getReference("gs://snapcat-63f5b.appspot.com/IMG_0090.jpeg")
        val httpsReference = storage.getReferenceFromUrl(
            "https://firebasestorage.googleapis.com/v0/b/snapcat-63f5b.appspot.com/o/IMG_0090.jpeg?alt=media&token=d20f023f-27d0-428f-9635-351ad84e343d"
        )
        //val testDown = getDownloadUrl()
    }




   /*fun loadImages(){
       val imageRef = storageRef.child("images/").listAll()
       imageRef.addOnSuccessListener { res ->
           res.items.forEach { itemRef ->
               itemRef.downloadUrl.addOnSuccessListener { url ->
                   val bitUrl: Bitmap = url as Bitmap
                   data.add(ItemsViewModel(bitUrl as Int))
               }.addOnFailureListener { error ->
                   error.printStackTrace()
               }
           }
       }.addOnFailureListener { error ->
           error.printStackTrace()
       }
   }*/


/*    fun getImages(){
        val storage: FirebaseStorage = FirebaseStorage.getInstance()
        var storageRef = storage.getReference().child("images/")
        storageRef.listAll()
            .addOnSuccessListener {
                @Override
                fun onSuccess(listResult: ListResult){
                    val srList = listResult.getItems()
                    for (sr in srList) {
                        sr.getBytes(1024*1024)
                            .addOnSuccessListener {
                                @Override
                                fun onSuccess(bytes: ByteArray) {
                                    val bitmap: Bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                                    createImage(this@MainActivity, bitmap)
                                }
                            }
                    }
                }
            }

    }
    */



    fun createPostScreen(view: View){
        val load = Intent(this, CreateActivity::class.java)
        startActivity(load)
    }



    fun onPopupClick(view: View) {
        // inflate the layout of the popup window
        // inflate the layout of the popup window
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val popupView: View = inflater.inflate(R.layout.popup_window, null)
        val popupView: View = inflater.inflate(R.layout.popup_window, null)

        // create the popup window

        // create the popup window
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true // lets taps outside the popup also dismiss it

        val popupWindow = PopupWindow(popupView, width, height, focusable)

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        // dismiss the popup window when touched

        // dismiss the popup window when touched
        popupView.setOnTouchListener { v, event ->
            popupWindow.dismiss()
            true
        }
    }



    fun readData(){
        myRef.child("images")
    }
*/
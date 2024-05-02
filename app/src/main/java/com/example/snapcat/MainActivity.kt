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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage


class MainActivity : AppCompatActivity() {
    val storage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageRef = storage.reference

    val database = FirebaseDatabase.getInstance()
    val databaseRef = database.getReference("images")

    var imageData = ArrayList<String>()
    var imageDescription = ArrayList<String>()
    var data = ArrayList<ItemsViewModel>()

    lateinit var auth: FirebaseAuth


    //lateinit var data: ArrayList<ItemsViewModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        auth = Firebase.auth
        val currentUser = auth.currentUser
        auth.signInAnonymously()

        //getRelationalDatabase()
        getRecyclerData()

        val adapter = CustomAdapter(data)
        recyclerView.adapter = adapter
    }




    fun getRecyclerData(){
        /*
        imageData.add(R.drawable.img_0090)
        imageData.add(R.drawable.img_0091)
        imageData.add(R.drawable.img_0092)

        imageDescription.add("The cat's asleep in ISER.")
        imageDescription.add("The cat is vibing on a radiator.")
        imageDescription.add("Pebbles is asleep on a blue chair.")
        */



        for (i in 1 .. 2){
            imageData.add(database.reference.child("images").child(i.toString()).child("uid").get().toString())
            imageDescription.add(database.reference.child("images").child(i.toString()).child("info").get().toString())
        }



        for (i in 1..data.size) {
            data.add(ItemsViewModel(imageData[i - 1], imageDescription[i - 1]))
        }
    }


    fun onPopupClick(view: View) {
        // inflate the layout of the popup window
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_window, null)
        // create the popup window
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true // lets taps outside the popup also dismiss it
        val popupWindow = PopupWindow(popupView, width, height, focusable)
        // show the popup windows which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        // dismiss the popup window when touched
        popupView.setOnTouchListener { v, event ->
            popupWindow.dismiss()
            true
        }
    }



    fun createPostScreen(view: View){
        val load = Intent(this, CreateActivity::class.java)
        startActivity(load)
    }
}
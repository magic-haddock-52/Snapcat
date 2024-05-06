package com.example.snapcat

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
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
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        auth = Firebase.auth
        auth.signInAnonymously()


        //getRecyclerData()
        backup()

        val adapter = CustomAdapter(data)
        recyclerView.adapter = adapter
    }



    fun getRecyclerData(){
        for (i in 1..6) {
            var imageString = databaseRef.child(i.toString()).child("uri").get()
            var descString = databaseRef.child(i.toString()).child("text").get()
            imageData.add(imageString.toString())
            imageDescription.add(descString.toString())
        }


        for (i in 1..2) {
            data.add(ItemsViewModel(imageData[i - 1], imageDescription[i - 1]))
        }
    }


    fun backup(){
        imageData.add("https://firebasestorage.googleapis.com/v0/b/snapcat-63f5b.appspot.com/o/IMG_0090.jpeg?alt=media&token=d20f023f-27d0-428f-9635-351ad84e343d")
        imageData.add("https://firebasestorage.googleapis.com/v0/b/snapcat-63f5b.appspot.com/o/IMG_0091.jpeg?alt=media&token=96ee66fc-04df-4c6e-9875-d77e2df32914")
        imageData.add("https://firebasestorage.googleapis.com/v0/b/snapcat-63f5b.appspot.com/o/IMG_0092.jpeg?alt=media&token=ec524d68-1cc4-4c1a-b6c5-c1a3150954a1")
        imageData.add("https://firebasestorage.googleapis.com/v0/b/snapcat-63f5b.appspot.com/o/IMG_0093.jpeg?alt=media&token=5cdebbc1-ac5f-4b51-bb27-3ce4a67283bc")
        imageData.add("https://firebasestorage.googleapis.com/v0/b/snapcat-63f5b.appspot.com/o/IMG_0094.jpeg?alt=media&token=48de711e-aaed-450c-922e-8af5fa7d0df8")
        imageData.add("https://firebasestorage.googleapis.com/v0/b/snapcat-63f5b.appspot.com/o/IMG_0095.jpeg?alt=media&token=69a28a49-69c8-4976-8fe3-8625bf22829d")

        imageDescription.add("The cat's asleep on the sofa.")
        imageDescription.add("Radiator!")
        imageDescription.add("Half asleep")
        imageDescription.add("Properly asleep")
        imageDescription.add("wide shot!")
        imageDescription.add("bread")

        for (i in 1..6){
            data.add(ItemsViewModel(imageData[i - 1], imageDescription[i - 1]))
        }
    }


    fun createPostScreen(view: View){
        val load = Intent(this, CreateActivity::class.java)
        startActivity(load)
    }
}
package com.example.snapcat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        // Start the next activity after a delay
        val intent = Intent(this, MainActivity::class.java)
        val splashScreenDuration = 3000 // 3 seconds
        Thread {
            Thread.sleep(splashScreenDuration.toLong())
            startActivity(intent)
            finish()
        }.start()
    }
}
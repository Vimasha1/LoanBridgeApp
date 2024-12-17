package com.example.loanbridgeapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TrackPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_track_page)

        val icnHome: ImageView = findViewById(R.id.icnHome)
        icnHome.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        val trackIcon: ImageView = findViewById(R.id.trackIcon)
        trackIcon.setOnClickListener {
            val intent = Intent(this, TrackPage::class.java)
            startActivity(intent)
        }

        val profileIcon: ImageView = findViewById(R.id.profileIcon)
        profileIcon.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        val applyIcon: ImageView = findViewById(R.id.applyIcon)
        applyIcon.setOnClickListener {
            val intent = Intent(this, Apply::class.java)
            startActivity(intent)
        }

        val dial: ImageView = findViewById(R.id.dial)
        dial.setOnClickListener {
            val phoneUri = Uri.parse("tel:0712345678")

            val intent = Intent(Intent.ACTION_DIAL, phoneUri)
            val chooser = Intent.createChooser(intent, "Open with")
            if (chooser.resolveActivity(packageManager) != null) {
                startActivity(chooser)
            }else {
                Log.e("OpenPhone", "No application found to handle the intent")
            }
        }

        val btnNavigate: Button = findViewById(R.id.Seetasks)
        btnNavigate.setOnClickListener {
            val intent = Intent(this, MyTasks::class.java)
            startActivity(intent)
        }


    }
}
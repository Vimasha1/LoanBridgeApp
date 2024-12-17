package com.example.loanbridgeapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val gotUser = findViewById<TextView>(R.id.gotUser)

        val intent = intent
        val name = intent.getStringExtra("USER_NAME")
        gotUser.text = "Hello, $name!"


        val icnHome: ImageView= findViewById(R.id.icnHome)
        icnHome.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        val trackIcon: ImageView= findViewById(R.id.trackIcon)
        trackIcon.setOnClickListener {
            val intent = Intent(this, TrackPage::class.java)
            startActivity(intent)
        }

        val profileIcon: ImageView= findViewById(R.id.profileIcon)
        profileIcon.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }

        val applyIcon: ImageView= findViewById(R.id.applyIcon)
        applyIcon.setOnClickListener {
            val intent = Intent(this, Apply::class.java)
            startActivity(intent)
        }


        val eduLoan: ImageView = findViewById(R.id.eduLoan)
        eduLoan.setOnClickListener {
            val webpage = Uri.parse("https://studentaid.gov/")
            val intent = Intent(Intent.ACTION_VIEW, webpage)

            // Create a chooser to force the user to pick a browser, even if only one is available
            val chooser = Intent.createChooser(intent, "Open with")

            // Check if there is an app to handle the intent
            if (chooser.resolveActivity(packageManager) != null) {
                startActivity(chooser)
            } else {
                Log.e("OpenWeb", "No application found to handle the intent")
            }
        }

        val homeLoan: ImageView = findViewById(R.id.homeLoan)
        homeLoan.setOnClickListener {
            val webpage = Uri.parse("https://www.zillow.com/homeloans")
            val intent = Intent(Intent.ACTION_VIEW, webpage)

            // Create a chooser to force the user to pick a browser, even if only one is available
            val chooser = Intent.createChooser(intent, "Open with")

            // Check if there is an app to handle the intent
            if (chooser.resolveActivity(packageManager) != null) {
                startActivity(chooser)
            } else {
                Log.e("OpenWeb", "No application found to handle the intent")
            }
        }

        val personalLoan: ImageView = findViewById(R.id.personalLoan)
        personalLoan.setOnClickListener {
            val webpage = Uri.parse("https://www.lendingtree.com/")
            val intent = Intent(Intent.ACTION_VIEW, webpage)

            // Create a chooser to force the user to pick a browser, even if only one is available
            val chooser = Intent.createChooser(intent, "Open with")

            // Check if there is an app to handle the intent
            if (chooser.resolveActivity(packageManager) != null) {
                startActivity(chooser)
            } else {
                Log.e("OpenWeb", "No application found to handle the intent")
            }
        }

        val autoLoan: ImageView = findViewById(R.id.autoLoan)
        autoLoan.setOnClickListener {
            val webpage = Uri.parse("https://www.autotrader.co.uk")
            val intent = Intent(Intent.ACTION_VIEW, webpage)

            // Create a chooser to force the user to pick a browser, even if only one is available
            val chooser = Intent.createChooser(intent, "Open with")

            // Check if there is an app to handle the intent
            if (chooser.resolveActivity(packageManager) != null) {
                startActivity(chooser)
            } else {
                Log.e("OpenWeb", "No application found to handle the intent")
            }
        }






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
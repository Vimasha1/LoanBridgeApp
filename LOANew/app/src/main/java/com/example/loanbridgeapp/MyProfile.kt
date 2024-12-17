package com.example.loanbridgeapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MyProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_profile)


        val gotUsername = findViewById<TextView>(R.id.gotUsername)

        val intent = intent
        val name = intent.getStringExtra("name")
        gotUsername.text = "Full name: $name"

        val gotDOB = findViewById<TextView>(R.id.gotDOB)


        val DOB = intent.getStringExtra("DOB")
        gotDOB.text = "DOB: $DOB"

        val gotNIC = findViewById<TextView>(R.id.gotNIC)


        val NIC = intent.getStringExtra("NIC")
        gotNIC.text = "NIC: $NIC"

        val gotEmail = findViewById<TextView>(R.id.gotEmail)


        val Email = intent.getStringExtra("Email")
        gotEmail.text = "Email: $Email"

        val gotPhoneNumber = findViewById<TextView>(R.id.gotPhoneNumber)


        val PhoneNumber = intent.getStringExtra("PhoneNumber")
        gotPhoneNumber.text = "Phone Number: $PhoneNumber"

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
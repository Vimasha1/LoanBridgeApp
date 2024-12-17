package com.example.loanbridgeapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

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

        val createProfile: Button= findViewById(R.id.createProfile)
        createProfile.setOnClickListener {
            val intent = Intent(this, MyProfile::class.java)
            val name = findViewById<EditText>(R.id.FullName).text.toString()
            val DOB = findViewById<EditText>(R.id.DOB).text.toString()
            val NIC = findViewById<EditText>(R.id.NIC).text.toString()
            val Email = findViewById<EditText>(R.id.Email).text.toString()
            val PhoneNumber = findViewById<EditText>(R.id.PhoneNumber).text.toString()


            intent.putExtra("name", name)
            intent.putExtra("DOB", DOB)
            intent.putExtra("NIC", NIC)
            intent.putExtra("Email", Email)
            intent.putExtra("PhoneNumber", PhoneNumber)
            startActivity(intent)

        }

/*
        val FullName: EditText = findViewById(R.id.FullName)
        FullName.setOnClickListener {
            val name = FullName.text.toString()
            val intent = Intent(this, MyProfile::class.java)
            intent.putExtra("name", name)
            startActivity(intent)
        }

        val DOB: EditText = findViewById(R.id.DOB)
        DOB.setOnClickListener {
            val DOB = DOB.text.toString()
            val intent = Intent(this, MyProfile::class.java)
            intent.putExtra("DOB", DOB)
            startActivity(intent)
        }

        val NIC: EditText = findViewById(R.id.NIC)
        NIC.setOnClickListener {
            val NIC = NIC.text.toString()
            val intent = Intent(this, MyProfile::class.java)
            intent.putExtra("NIC", NIC)
            startActivity(intent)
        }

        val Email: EditText = findViewById(R.id.Email)
        Email.setOnClickListener {
            val Email = Email.text.toString()
            val intent = Intent(this, MyProfile::class.java)
            intent.putExtra("Email", Email)
            startActivity(intent)
        }

        val PhoneNumber: EditText = findViewById(R.id.PhoneNumber)
        PhoneNumber.setOnClickListener {
            val PhoneNumber = PhoneNumber.text.toString()
            val intent = Intent(this, MyProfile::class.java)
            intent.putExtra("PhoneNumber", PhoneNumber)
            startActivity(intent)
        }*/

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
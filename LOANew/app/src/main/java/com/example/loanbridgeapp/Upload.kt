package com.example.loanbridgeapp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.Chronometer
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Upload : AppCompatActivity() {

    private lateinit var remainingTimeTextView: TextView
    private lateinit var countDownTimer: CountDownTimer
    private var timerRunning = false
    private lateinit var submitButton: Button
    private lateinit var saveChanges: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upload)

        submitButton = findViewById(R.id.subFinal)
        remainingTimeTextView = findViewById(R.id.remainingTimeTextView)

        // Start the countdown timer
        startCountDownTimer()

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

        val subFinal: Button = findViewById(R.id.subFinal)
        subFinal.setOnClickListener {
            val intent = Intent(this, ThankYou::class.java)
            startActivity(intent)
        }




        val camIcon: ImageView = findViewById(R.id.camIcon)
        camIcon.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val chooser = Intent.createChooser(intent, "Open with")
            if (chooser.resolveActivity(packageManager) != null) {
                startActivity(chooser)
            } else {
                Log.e("OpenCam", "No application found to handle the intent")
            }
        }
        saveChanges = findViewById(R.id.saveChanges)


        saveChanges.setOnClickListener {
            saySaved()
            if (timerRunning) {

                countDownTimer.cancel()
                timerRunning = false
            }
        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun saySaved() {
        Toast.makeText(this, "Document Saved", Toast.LENGTH_LONG).show()

    }

    private fun startCountDownTimer() {
        countDownTimer = object : CountDownTimer(60000, 1000) { // 1 minute
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000).toInt()
                remainingTimeTextView.text = String.format("%02d:%02d", secondsRemaining / 60, secondsRemaining % 60)
            }



            override fun onFinish() {
                sendNotification() // Trigger notification
                // Disable the upload button
                submitButton.isEnabled = false
                submitButton.alpha = 0.5f // Optional: Change opacity to indicate it's disabled
                saveChanges.isEnabled = false
                saveChanges.alpha = 0.5f // Optional: Change opacity to indicate it's disabled

            }


        }.start()
        timerRunning = true
    }

    private fun sendNotification() {
        val notificationBuilder = NotificationCompat.Builder(this, "YOUR_CHANNEL_ID")
            .setSmallIcon(R.drawable.appicon1) // Replace with your icon
            .setContentTitle("Upload Reminder")
            .setContentText("Time's up for your document upload!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(0, 500, 1000)) // Vibration pattern
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

        val notificationManager = NotificationManagerCompat.from(this)

        // Create the notification channel if necessary (API 26+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("YOUR_CHANNEL_ID", "Upload Notifications", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            return
        }
        notificationManager.notify(1, notificationBuilder.build())
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendNotification() // Call to send notification after permission is granted
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onPause() {
        super.onPause()

        if (timerRunning) {
            countDownTimer.cancel() // Stop the countdown if running
        }
    }

    override fun onResume() {
        super.onResume()
        if (!timerRunning) {
            startCountDownTimer() // Restart the timer if it was paused
        }



}
}
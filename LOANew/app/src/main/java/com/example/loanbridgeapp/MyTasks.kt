package com.example.loanbridgeapp

import TaskAdapter
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

class MyTasks : AppCompatActivity() {

    private lateinit var rvTasks: RecyclerView
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_tasks)

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

        rvTasks = findViewById(R.id.TaskList)

        // Load tasks from SharedPreferences
        loadTasks()

        val fabAddTask: FloatingActionButton = findViewById(R.id.floatAdd)
        fabAddTask.setOnClickListener {
            // Intent to go to AddTaskActivity or show a dialog to add a task
            val intent = Intent(this, AddTask::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        loadTasks() // Refresh task list when returning
    }


    private fun loadTasks() {
        val sharedPreferences = getSharedPreferences("tasks", MODE_PRIVATE)
        val tasksJson = sharedPreferences.getString("tasks_list", "[]") ?: "[]"
        val tasks = Gson().fromJson(tasksJson, Array<Task>::class.java).toMutableList()

        // Set up the adapter with the loaded tasks
        adapter = TaskAdapter(this, tasks)
        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = adapter

        // Update the widget with the new task list
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val widgetIds = appWidgetManager.getAppWidgetIds(ComponentName(this, NewAppWidget::class.java))
        appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.task_list)
    }
}
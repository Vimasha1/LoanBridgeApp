package com.example.loanbridgeapp

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson

class TaskDetails : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvDescript: TextView
    private lateinit var tvDueDate: TextView
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button
    private lateinit var editTaskLauncher: ActivityResultLauncher<Intent>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_task_details)
        editTaskLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                finish() // Go back to MyTasks activity
            }
        }

        tvName = findViewById(R.id.tvName)
        tvDescript = findViewById(R.id.tvDescript)
        tvDueDate = findViewById(R.id.tvDueDate)
        btnEdit = findViewById(R.id.Edit)
        btnDelete = findViewById(R.id.Delete)

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

        val taskId = intent.getIntExtra("TASK_ID", -1)

        if(taskId != -1){
            val task = fetchTaskDetails(taskId)

            tvName.text = task.taskName
            tvDescript.text = task.taskDescription
            tvDueDate.text = task.dueDate

            btnEdit.setOnClickListener {
                val intent = Intent(this, AddTask::class.java)
                intent.putExtra("TASK_ID", taskId)
                editTaskLauncher.launch(intent)
            }

            btnDelete.setOnClickListener {
                deleteTask(taskId)
                Toast.makeText(this, "Task deleted successfully!", Toast.LENGTH_SHORT).show()
                finish()
            }
        } else {
            // Handle error case where no valid taskId is provided
            Toast.makeText(this, "Error fetching task details.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteTask(taskId: Int) {
        val sharedPreferences = getSharedPreferences("tasks", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val tasksJson = sharedPreferences.getString("tasks_list", "[]") ?: "[]"
        val tasksList = Gson().fromJson(tasksJson, Array<Task>::class.java).toMutableList()

        if (taskId < tasksList.size) {
            tasksList.removeAt(taskId)
            editor.putString("tasks_list", Gson().toJson(tasksList))
            editor.apply()

            // Refresh the widget after deleting the task
            refreshWidget()
        } else {
            Toast.makeText(this, "Unable to delete task!", Toast.LENGTH_SHORT).show()
        }
    }



    private fun fetchTaskDetails(taskId: Int): Task {
        val sharedPreferences = getSharedPreferences("tasks", MODE_PRIVATE)
        val tasksJson = sharedPreferences.getString("tasks_list", "[]") ?: "[]"
        val tasksList = Gson().fromJson(tasksJson, Array<Task>::class.java)
        return tasksList[taskId]
    }

    private fun refreshWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val widgetIds = appWidgetManager.getAppWidgetIds(ComponentName(this, NewAppWidget::class.java))
        appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.task_list)

        // Trigger the onUpdate method of the widget
        val intent = Intent(this, NewAppWidget::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds)
        sendBroadcast(intent)
    }





}
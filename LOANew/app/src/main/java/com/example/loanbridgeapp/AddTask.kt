package com.example.loanbridgeapp

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class AddTask : AppCompatActivity() {
    private lateinit var edtTaskName: EditText
    private lateinit var edtDescription: EditText
    private lateinit var tvDueDate: TextView
    private lateinit var btnSave: Button
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        edtTaskName = findViewById(R.id.editName)
        edtDescription = findViewById(R.id.editDescription)
        tvDueDate = findViewById(R.id.editDueDate)
        btnSave = findViewById(R.id.Save)

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

        if (taskId != -1) {
            loadTaskDetails(taskId)
        }

        tvDueDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = "${selectedDay.toString().padStart(2, '0')}/" +
                            "${(selectedMonth + 1).toString().padStart(2, '0')}/" +
                            selectedYear.toString()
                    tvDueDate.text = selectedDate
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        btnSave.setOnClickListener {
            val name = edtTaskName.text.toString()
            val description = edtDescription.text.toString()
            val date = tvDueDate.text.toString()

            if (name.isNotEmpty() && date.isNotEmpty()) {
                if (isDateValid(date)) {
                    if (taskId != -1) {
                        updateTask(taskId, Task(name, description, date))
                        setResult(RESULT_OK)
                    } else {
                        saveTask(Task(name, description, date))
                        setResult(RESULT_OK)
                    }

                    setReminder(name, description, date)
                    Toast.makeText(this, "Task saved successfully!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Please select a future date.", Toast.LENGTH_SHORT).show()
                }
            } else {
                val errorMsg = if (date.isEmpty()) "Please select a date." else "Please fill out required fields!"
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isDateValid(date: String): Boolean {
        val selectedDate = dateFormat.parse(date) ?: return false
        val currentDate = Calendar.getInstance()
        currentDate.set(Calendar.HOUR_OF_DAY, 0)
        currentDate.set(Calendar.MINUTE, 0)
        currentDate.set(Calendar.SECOND, 0)

        return selectedDate.after(currentDate.time)
    }

    private fun saveTask(task: Task) {
        val sharedPreferences = getSharedPreferences("tasks", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Retrieve the current list of tasks from SharedPreferences
        val tasksJson = sharedPreferences.getString("tasks_list", "[]") ?: "[]"
        val tasksList = Gson().fromJson(tasksJson, Array<Task>::class.java).toMutableList()

        // Add the new task to the list
        tasksList.add(task)

        // Save the updated list back to SharedPreferences
        editor.putString("tasks_list", Gson().toJson(tasksList))
        editor.apply()

        // Notify the widget to refresh the task list
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val widgetIds = appWidgetManager.getAppWidgetIds(ComponentName(this, NewAppWidget::class.java))
        appWidgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.task_list)

        // Trigger the onUpdate method of the widget
        val intent = Intent(this, NewAppWidget::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds)
        sendBroadcast(intent)
    }


    private fun loadTaskDetails(taskId: Int) {
        val sharedPreferences = getSharedPreferences("tasks", MODE_PRIVATE)
        val tasksJson = sharedPreferences.getString("tasks_list", "[]") ?: "[]"
        val tasksList = Gson().fromJson(tasksJson, Array<Task>::class.java)

        val task = tasksList[taskId]
        edtTaskName.setText(task.taskName)
        edtDescription.setText(task.taskDescription)
        tvDueDate.text = task.dueDate
    }

    private fun updateTask(taskId: Int, updatedTask: Task) {
        val sharedPreferences = getSharedPreferences("tasks", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val tasksJson = sharedPreferences.getString("tasks_list", "[]") ?: "[]"
        val tasksList = Gson().fromJson(tasksJson, Array<Task>::class.java).toMutableList()

        if (taskId < tasksList.size) {
            tasksList[taskId] = updatedTask
            editor.putString("tasks_list", Gson().toJson(tasksList))
            editor.apply()

            // Refresh the widget after updating the task
            refreshWidget()
        } else {
            Toast.makeText(this, "Unable to update task!", Toast.LENGTH_SHORT).show()
        }
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


    private fun setReminder(taskName: String, taskDescription: String, dueDate: String) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Check if the permission is granted
        if (alarmManager.canScheduleExactAlarms()) {
            val calendar = Calendar.getInstance()
            val date = dateFormat.parse(dueDate)
            calendar.time = date!!
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)

            // Subtract one day to set the reminder for the previous day
            calendar.add(Calendar.DAY_OF_YEAR, -1)

            val intent = Intent(this, ReminderReceiver::class.java).apply {
                putExtra("TASK_NAME", taskName)
                putExtra("TASK_DESCRIPTION", taskDescription)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Use FLAG_IMMUTABLE for safety
            )

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        } else {
            // Request permission to schedule exact alarms
            val intent = Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            startActivity(intent)
        }
    }
}

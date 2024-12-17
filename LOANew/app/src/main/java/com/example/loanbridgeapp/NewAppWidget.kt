package com.example.loanbridgeapp

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.google.gson.Gson
import android.content.SharedPreferences

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

    val tasks = loadTasks(context)
    val views = RemoteViews(context.packageName, R.layout.widget_layout)
    views.removeAllViews(R.id.task_list)

    for (task in tasks) {
        val taskView = RemoteViews(context.packageName, R.layout.widget_task_item)
        taskView.setTextViewText(R.id.task_text, task.taskName) // Assuming Task has a title property
        views.addView(R.id.task_list, taskView)
    }

    appWidgetManager.updateAppWidget(appWidgetId, views)
}

private fun loadTasks(context: Context): List<Task> {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("tasks", Context.MODE_PRIVATE)
    val tasksJson = sharedPreferences.getString("tasks_list", "[]") ?: "[]"
    return Gson().fromJson(tasksJson, Array<Task>::class.java).toList()
}
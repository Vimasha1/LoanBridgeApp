package com.example.loanbridgeapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    val taskName: TextView = itemView.findViewById(R.id.tv_task_name)
    val tvDate: TextView = itemView.findViewById(R.id.task_dueDate)

}
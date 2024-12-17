import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loanbridgeapp.R
import com.example.loanbridgeapp.Task
import com.example.loanbridgeapp.TaskDetails
import com.example.loanbridgeapp.TaskViewHolder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager


class TaskAdapter(
    private val context: Context,
    private val tasks: List<Task>

    ):RecyclerView.Adapter<TaskViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task,parent,false)
        return TaskViewHolder(itemView)
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskName.text = task.taskName
        holder.tvDate.text = task.dueDate

        holder.itemView.setOnClickListener{
            val intent = Intent(context, TaskDetails::class.java)
            intent.putExtra("TASK_ID", position)
            context.startActivity(intent)

        }
    }


}
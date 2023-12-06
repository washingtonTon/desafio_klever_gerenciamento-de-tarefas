// MainActivity.kt
package com.example.gerenciamentodetarefas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var taskList: MutableList<String> = mutableListOf()
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(taskList) { position -> deleteTask(position) }
        recyclerView.adapter = adapter

        val addButton: View = findViewById(R.id.addButton)
        addButton.setOnClickListener { addTask() }
    }

    fun addTask() {
        val editText: EditText = findViewById(R.id.editTextTask)
        val task = editText.text.toString()
        if (task.isNotEmpty()) {
            taskList.add(task)
            adapter.notifyItemInserted(taskList.size - 1)
            editText.text.clear()
            Toast.makeText(this, "Tarefa adicionada!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Por favor, insira uma tarefa.", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteTask(position: Int) {
        taskList.removeAt(position)
        adapter.notifyItemRemoved(position)
        Toast.makeText(this, "Tarefa deletada!", Toast.LENGTH_SHORT).show()
    }
}

// TaskAdapter.kt
class TaskAdapter(private val tasks: MutableList<String>, private val onDelete: (Int) -> Unit) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textViewTask)
        val deleteButton: View = view.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.textView.text = tasks[position]
        holder.deleteButton.setOnClickListener { onDelete(position) }
    }

    override fun getItemCount() = tasks.size
}
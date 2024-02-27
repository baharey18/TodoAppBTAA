package com.example.todoappbtaa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoApp()
        }
    }
}

@Composable
fun ToDoApp() {
    var tasks by remember { mutableStateOf(listOf<Task>()) }
    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Add a task") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if(text.isNotBlank()) {
                    tasks = tasks + Task(id = tasks.size + 1, content = text)
                    text = ""
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add Task")
        }
        TaskList(tasks = tasks, onTaskCompleted = { task ->
            tasks = tasks.map {
                if (it.id == task.id) it.copy(isCompleted = !it.isCompleted) else it
            }
        }, onRemoveCompleted = {
            tasks = tasks.filter { !it.isCompleted }
        })
    }
}

@Composable
fun TaskList(tasks: List<Task>, onTaskCompleted: (Task) -> Unit, onRemoveCompleted: () -> Unit) {
    Column {
        LazyColumn {
            items(items = tasks, key = { it.id }) { task ->
                TaskItem(task = task, onTaskCompleted = onTaskCompleted)
            }
        }
        Button(onClick = onRemoveCompleted, modifier = Modifier.padding(top = 8.dp)) {
            Text("Remove Completed Tasks")
        }
    }
}

@Composable
fun TaskItem(task: Task, onTaskCompleted: (Task) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text(task.content, modifier = Modifier.weight(1f))
        Button(onClick = { onTaskCompleted(task) }) {
            Text(if (task.isCompleted) "Mark Incomplete" else "Mark Complete")
        }
    }
}


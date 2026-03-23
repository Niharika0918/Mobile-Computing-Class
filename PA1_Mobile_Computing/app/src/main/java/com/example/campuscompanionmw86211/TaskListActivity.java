package com.example.campuscompanionmw86211;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private RecyclerView recyclerTasks;
    private List<Task> taskList;

    private static final String TAG = "TaskListLifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        Log.d(TAG, "onCreate called");

        tvWelcome = findViewById(R.id.tvWelcome);
        recyclerTasks = findViewById(R.id.recyclerTasks);

        // Get username from Intent
        String username = getIntent().getStringExtra("USERNAME");

        if (username == null || username.trim().isEmpty()) {
            username = "Student";
        }

        // Set welcome message
        tvWelcome.setText(getString(R.string.welcome_message, username));

        // Create task list (NEW tasks)
        taskList = new ArrayList<>();
        taskList.add(new Task("Finish Lab Work", "Complete Android lab exercises", "High"));
        taskList.add(new Task("Study for Finals", "Revise all important topics", "High"));
        taskList.add(new Task("Team Meeting", "Discuss group project progress", "Medium"));
        taskList.add(new Task("Read Chapter", "Read next chapter from textbook", "Medium"));
        taskList.add(new Task("Practice Coding", "Solve Java problems", "High"));
        taskList.add(new Task("Write Notes", "Organize lecture notes neatly", "Low"));
        taskList.add(new Task("Clean Workspace", "Organize study desk", "Low"));
        taskList.add(new Task("Plan Schedule", "Prepare weekly study plan", "Medium"));

        // Setup adapter
        TaskAdapter adapter = new TaskAdapter(taskList, task -> {
            Intent intent = new Intent(TaskListActivity.this, TaskDetailActivity.class);
            intent.putExtra("TASK_TITLE", task.getTitle());
            intent.putExtra("TASK_DESCRIPTION", task.getDescription());
            intent.putExtra("TASK_PRIORITY", task.getPriority());
            startActivity(intent);
        });

        recyclerTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerTasks.setAdapter(adapter);
    }

    // Lifecycle logs
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }
}
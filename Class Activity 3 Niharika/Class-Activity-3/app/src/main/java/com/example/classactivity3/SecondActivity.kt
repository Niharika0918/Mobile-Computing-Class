package com.example.classactivity3

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val displayTextView = findViewById<TextView>(R.id.textViewDisplay)

        // Retrieve the data using the Intent and the specific key
        val passedMessage = intent.getStringExtra("EXTRA_MESSAGE")

        // Display it in the TextView
        displayTextView.text = passedMessage
    }
}
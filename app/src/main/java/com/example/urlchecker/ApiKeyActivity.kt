package com.example.urlchecker

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ApiKeyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api_key)

        val linkText = findViewById<TextView>(R.id.linkText)
        linkText.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.virustotal.com/gui/join-us"))
            startActivity(browserIntent)
        }

        val apiKeyInput = findViewById<EditText>(R.id.apiKeyInput)
        val saveButton = findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val apiKey = apiKeyInput.text.toString().trim()
            if (apiKey.isEmpty()) {
                Toast.makeText(this, "Please enter your API key.", Toast.LENGTH_SHORT).show()
            } else {
                val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
                prefs.edit().putString("vtApiKey", apiKey).apply()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}
package com.example.urlchecker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val vtApiKey = "YOUR_API_KEY" // Replace with your VirusTotal API Key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val urlInput = findViewById<EditText>(R.id.urlInput)
        val scanButton = findViewById<Button>(R.id.scanButton)
        val resultText = findViewById<TextView>(R.id.resultText)

        scanButton.setOnClickListener {
            val url = urlInput.text.toString()
            scanUrl(url, resultText)
        }
    }

    private fun scanUrl(url: String, resultView: TextView) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.virustotal.com/vtapi/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(VirusTotalApi::class.java)
        api.checkUrl(vtApiKey, url).enqueue(object : Callback<VirusTotalResponse> {
            override fun onResponse(call: Call<VirusTotalResponse>, response: Response<VirusTotalResponse>) {
                val res = response.body()
                if (res != null) {
                    val status = if (res.positives > 0) "Malicious" else "Safe"
                    resultView.text = "URL: ${'$'}{res.url}\nStatus: ${'$'}status\nDetections: ${'$'}{res.positives}/${'$'}{res.total}"
                } else {
                    resultView.text = "No results found."
                }
            }
            override fun onFailure(call: Call<VirusTotalResponse>, t: Throwable) {
                resultView.text = "Error: ${'$'}{t.message}"
            }
        })
    }
}
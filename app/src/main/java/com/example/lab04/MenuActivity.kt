package com.example.lab04

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val btnSharedPreferences = findViewById<Button>(R.id.btnSharedPreferences)

        btnSave.setOnClickListener{
            var intent = Intent(this, SaveActivity::class.java)
            startActivity(intent)
        }

        btnSearch.setOnClickListener{
            var intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        btnSharedPreferences.setOnClickListener{
            var intent = Intent(this, SharedPreferencesActivity::class.java)
            startActivity(intent)
        }
    }
}
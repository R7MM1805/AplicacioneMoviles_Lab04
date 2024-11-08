package com.example.lab04

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ReadSharedPreferencesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_read_shared_preferences)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun buscarPreferencia(view: View) {
        val nombreShared = findViewById<EditText>(R.id.txtNameSharedPreference)
        val prefs = getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE)
        val cadena = prefs.getString(nombreShared.text.toString(), "")
        val textView1 = findViewById<TextView>(R.id.resultadoShared)
        textView1.text = "valor: $cadena"
    }

}
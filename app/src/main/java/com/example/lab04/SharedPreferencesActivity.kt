package com.example.lab04

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SharedPreferencesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shared_preferences)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun saveSharedPreference(view: View) {

        val name = findViewById<EditText>(R.id.txtNameSharedPreference).text.toString()
        val value = findViewById<EditText>(R.id.txtValueSharedPreference).text.toString()
        val validateSaveShared = validateRegisterSharedPreference(name, value)

        if(validateSaveShared == ""){
            val prefs = getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString(name, value)
            editor.apply()
            Toast.makeText(applicationContext, "Dato grabado en el SharedPreferences", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(applicationContext, validateSaveShared, Toast.LENGTH_SHORT).show()
        }
    }

    fun validateRegisterSharedPreference(name: String?, value: String?) : String {
        val response = validateString(name, "Se requiere el nombre de la preferencia")
        return if(response == "") validateString(value, "Se requiere el valor de la preferencia") else response
    }

    fun validateString(value: String?, message: String) : String{
        if(value != null && value.trim() != "") return ""
        return message
    }


    fun goToReadSharedPreference(view: View) {
        val intent = Intent(this.baseContext, ReadSharedPreferencesActivity::class.java)
        startActivity(intent)
    }

}
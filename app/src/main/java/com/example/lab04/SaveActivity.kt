package com.example.lab04

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab04.DAO.DAOException
import com.example.lab04.DAO.MusicalGenreDAO
import com.example.lab04.Util.Tools

class SaveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_save)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun save(view: View) {
        val title = findViewById<EditText>(R.id.txtTitle)
        val description = findViewById<EditText>(R.id.txtDescription)

        val dao = MusicalGenreDAO(baseContext)
        try {
            val indice = dao.insertar(title.text.toString(), description.text.toString())
            if(indice > 0){
                title.setText("")
                description.setText("")
                Toast.makeText(baseContext, "Se insertó correctamente", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(baseContext, "Ocurrió un error al intentar insertar", Toast.LENGTH_SHORT).show()
            }

        } catch (e: DAOException) {
            Log.i(Tools.LOGTAG, "====> " + e.message)
        }

    }

    fun gotosearch(view: View) {
        val intent = Intent(baseContext, SearchActivity::class.java)
        startActivity(intent)
    }

}
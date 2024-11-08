package com.example.lab04

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab04.DAO.DAOException
import com.example.lab04.DAO.MusicalGenreDAO
import com.example.lab04.Model.MusicalGenre
import com.example.lab04.Util.Tools

class SearchActivity : AppCompatActivity() {

    lateinit var results : ArrayList<MusicalGenre>
    lateinit var lstResults : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun buscar(view: View?) {
        val criterio = findViewById<View>(R.id.criterio) as EditText
        val dao = MusicalGenreDAO(baseContext)
        try {
            results = dao.buscar(criterio.text.toString())
            val encontrados = arrayOfNulls<String>(results.size)
            var i = 0
            for (gm in results) {
                encontrados[i++] = gm.title + " | " + gm.description
            }
            val adaptador = ArrayAdapter(
                this.baseContext,
                android.R.layout.simple_list_item_1,
                encontrados
            )
            lstResults = findViewById(R.id.listaResultados)
            lstResults.setAdapter(adaptador)
            registerForContextMenu(lstResults)
        } catch (e: DAOException) {
            Log.i(Tools.LOGTAG, "BuscarActivity ==> " + e.message)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?,
                                     menuInfo: ContextMenu.ContextMenuInfo? ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu!!.setHeaderTitle("Seleccione una acción:")
        menu.add(0, v!!.getId(), 0, "Editar")
        menu.add(0, v.getId(), 0, "Eliminar")

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.title){
            "Eliminar" -> {
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                val index = info.position
                val gm = results[index]
                Log.i(Tools.LOGTAG, gm.title + "")
                val dao = MusicalGenreDAO(baseContext)
                try {
                    dao.eliminar(gm.id)
                    Toast.makeText(this, "Se eliminó el registro", Toast.LENGTH_SHORT).show()
                    buscar(info.targetView)

                } catch (e: DAOException) {
                    Log.i(Tools.LOGTAG, "onContextItemSelected ==> " + e.message)
                }
            }
            "Editar" -> {
                Toast.makeText(baseContext, "Editar...", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onContextItemSelected(item)
    }

}
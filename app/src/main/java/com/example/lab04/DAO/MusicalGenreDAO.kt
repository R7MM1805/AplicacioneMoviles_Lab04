package com.example.lab04.DAO

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.lab04.Model.MusicalGenre
import com.example.lab04.Util.Tools

class MusicalGenreDAO(myContext: Context)  {
    private var dbHelper: DbHelper = DbHelper(myContext)

    fun insertar(title: String, description: String): Long {
        Log.i(Tools.LOGTAG, "Ingresó al método insertar()")
        val indice: Long
        val values = ContentValues().apply {
            put("title", title)
            put("description", description)
        }
        val db = dbHelper.writableDatabase
        try {
            indice = db.insert(Tools.MYTABLE,null, values)
            return indice
        } catch (e: Exception) {
            throw DAOException("MusicalGenreDAO: Error al insertar: " + e.message)
        } finally {
            db.close()
        }
    }

    @SuppressLint("Range")
    fun obtener(): MusicalGenre {
        Log.i(Tools.LOGTAG, "Ingresó al método obtener()")
        val db = dbHelper.readableDatabase
        val modelo = MusicalGenre()
        try {
            val c: Cursor = db.rawQuery("select id, title, description from " + Tools.MYTABLE, null)
            if (c.count > 0) {
                c.moveToFirst()
                do {
                    val id: Int = c.getInt(c.getColumnIndex("id"))
                    val title: String = c.getString(c.getColumnIndex("title"))
                    val description: String = c.getString(c.getColumnIndex("description"))
                    modelo.id = id
                    modelo.title = title
                    modelo.description = description
                } while (c.moveToNext())
            }
            c.close()
        } catch (e: Exception) {
            throw DAOException("MusicalGenreDAO: Error al obtener: " + e.message)
        } finally {
            db.close()
        }
        return modelo
    }

    @SuppressLint("Range")
    fun buscar(criterio: String): ArrayList<MusicalGenre> {
        Log.i(Tools.LOGTAG, "Ingresó al método buscar()")
        val db = dbHelper.readableDatabase
        val lista = ArrayList<MusicalGenre>()
        try {
            val c: Cursor = db.rawQuery(
                "select id, title, description from " + Tools.MYTABLE + " where title like '%$criterio%' or description like '%$criterio%'",
                null
            )
            if (c.count > 0) {
                c.moveToFirst()
                do {
                    val id: Int = c.getInt(c.getColumnIndex("id"))
                    val title: String = c.getString(c.getColumnIndex("title"))
                    val description: String = c.getString(c.getColumnIndex("description"))
                    val model = MusicalGenre()
                    model.id = id
                    model.title = title
                    model.description = description
                    lista.add(model)
                } while (c.moveToNext())
            }
            c.close()
        } catch (e: Exception) {
            throw DAOException("MusicalGenreDAO: Error al obtener: " + e.message)
        } finally {
            db.close()
        }
        return lista
    }

    fun eliminar(id: Int) {
        Log.i(Tools.LOGTAG, "Ingresó al método eliminar()")
        val db = dbHelper.writableDatabase
        try {
            val args = arrayOf(id.toString())
            db.execSQL("DELETE FROM " + Tools.MYTABLE + " WHERE id=?", args)
        } catch (e: Exception) {
            throw DAOException("MusicalGenreDAO: Error al eliminar: " + e.message)
        } finally {
            db.close()
        }
    }

    fun eliminarTodos() {
        Log.i(Tools.LOGTAG, "Ingresó al método eliminarTodos()")
        val db = dbHelper.writableDatabase
        try {
            db.execSQL("DELETE FROM " + Tools.MYTABLE)
        } catch (e: Exception) {
            throw DAOException("MusicalGenreDAO: Error al eliminar todos: " + e.message)
        } finally {
            db.close()
        }
    }

}
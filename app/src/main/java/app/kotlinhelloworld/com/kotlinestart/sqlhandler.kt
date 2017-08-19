package app.kotlinhelloworld.com.kotlinestart

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class sqlhandler(context: Context) : SQLiteOpenHelper(context, dbname, factory, version) {
    companion object {
        internal val dbname = "user"
        internal val factory = null
        internal val version = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY, username varchar(200), email varchar(200))")
        /*
        db?.createTable("user", ifNotExists = true,
                columns = *arrayOf("id" to INTEGER + PRIMARY_KEY + UNIQUE,
                        "username" to TEXT,
                        "email" to TEXT))
         */
    }
    /*
    fun insert_data(array: Array)
    {

    }
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        //db.dropTable("user", true)
    }
}
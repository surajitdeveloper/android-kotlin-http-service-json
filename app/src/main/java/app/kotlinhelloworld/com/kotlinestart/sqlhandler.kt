package app.kotlinhelloworld.com.kotlinestart

import android.content.ContentValues
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
        db.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY, username varchar(200), email varchar(200))")
    }

    fun insert_data(tb: String, values: ContentValues)
    {
        val db: SQLiteDatabase = writableDatabase
        db.insert(tb, null, values)
    }
    fun fetch_count(): Int
    {
        val db: SQLiteDatabase = readableDatabase
        var return_var: Int = 0
        var c = db.rawQuery("select COUNT(*) as count from user", null)
        while (c.moveToNext())
        {
            var count = c.getString(c.getColumnIndex("count"))
            val return_var: Int = count.toInt()
            return return_var
        }
        return return_var
    }
    fun fetch_query(): ArrayList<String>
    {
        var list: ArrayList<String>
        list = arrayListOf()
        val db: SQLiteDatabase = readableDatabase
        var get_query = db.rawQuery("select * from user", null)
        while (get_query.moveToNext())
        {
            list.add(get_query.getString(get_query.getColumnIndex("id")))
            list.add(get_query.getString(get_query.getColumnIndex("username")))
            list.add(get_query.getString(get_query.getColumnIndex("email")))
        }
        return list
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        //db.dropTable("user", true)
    }
}
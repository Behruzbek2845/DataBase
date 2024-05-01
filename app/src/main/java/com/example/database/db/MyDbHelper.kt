package com.example.database.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.database.models.Users

class MyDbHelper(var context: Context):SQLiteOpenHelper(context, DB_NAME, null, VERSION), MyDbInterface {

    companion object{
        const val DB_NAME = "my_contact"
        const val TABLE_NAME = "contact_table"
        const val VERSION = 1

        const val ID = "id"
        const val NAME = "name"
        const val NUMBER = "number"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "create table $TABLE_NAME (id integer not null primary key autoincrement unique, $NAME text not null, $NUMBER text not null)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        }

    override fun addUser(user: Users) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, user.name)
        contentValues.put(NUMBER, user.number)
        database.insert(TABLE_NAME, null, contentValues)
        database.close()
        }

    override fun showUsers(): ArrayList<Users> {
        val list = ArrayList<Users>()
        val database = this.readableDatabase
        val query = "select * from $TABLE_NAME"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()){
            do {
                val user = Users(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
                )
                list.add(user)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun editUser(users: Users) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, users.name)
        contentValues.put(NUMBER, users.number)
        database.update(TABLE_NAME, contentValues, "$ID =?", arrayOf(users.id.toString()))
    }

    override fun deleteUser(users: Users) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$ID =?", arrayOf(users.id.toString()))
        database.close()
    }
}
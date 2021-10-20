package com.example.sqliteapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.net.UnknownServiceException

val DATABASE_NAME = "MyDB"
val TABLE_NAME = "Users"
val COL_ID = "id"
val COL_NAME = "name"
val COL_SURNAME = "surname"
val COL_NICKNAME = "nickname"
val COL_PASSWORD = "password"

class DataBaseHandler (var context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1){
    override fun onCreate(p0: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_NAME VARCHAR(256), " +
                "$COL_SURNAME VARCHAR(256), " +
                "$COL_NICKNAME VARCHAR(256), " +
                "$COL_PASSWORD VARCHAR(256))"

        p0?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(user : User){
        val db = this.writableDatabase
        //might need to be var
        val cv = ContentValues()
        cv.put(COL_NAME, user.name)
        cv.put(COL_SURNAME, user.surname)
        cv.put(COL_NICKNAME, user.nickName)
        cv.put(COL_PASSWORD, user.password)
        val result = db.insert(TABLE_NAME, null, cv)

        val long : Long = -1

        if (result == long){
            Toast.makeText(context, "Insertion failed", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Insertion successful", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("Recycle")
    fun checkLogin(username: String, password: String): Boolean{
        val db = this.writableDatabase

        val search = "SELECT * FROM $TABLE_NAME WHERE $COL_NICKNAME = '$username' AND $COL_PASSWORD = '$password'"

        val cursor = db.rawQuery(search,null)

        if (cursor.moveToFirst()){
            Toast.makeText(context, "Welcome, $username", Toast.LENGTH_LONG).show()
            return true
        }

        cursor?.close()
        db.close()
        return false
    }

    fun checkExistingUser(username: String): Boolean{
        val db = this.writableDatabase

        val search = "SELECT * FROM $TABLE_NAME WHERE $COL_NICKNAME = '$username'"

        val cursor = db.rawQuery(search,null)

        if (cursor.moveToFirst()){
            Toast.makeText(context, "User exists", Toast.LENGTH_LONG).show()
            return true
        }

        cursor?.close()
        db.close()
        return false
    }

    @SuppressLint("Recycle")
    fun getUserData(username: String): User? {
        val db = this.writableDatabase

        val search = "SELECT * FROM $TABLE_NAME WHERE $COL_NICKNAME = '$username'"

        val cursor = db.rawQuery(search,null)

        if (cursor.moveToFirst()){
            val id = cursor.getString(cursor.getColumnIndex(COL_ID)).toInt()
            val name = cursor.getString(cursor.getColumnIndex(COL_NAME))
            val surname = cursor.getString(cursor.getColumnIndex(COL_SURNAME))
            val userName = cursor.getString(cursor.getColumnIndex(COL_NICKNAME))
            val password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))

            val user = User(name, surname, userName, password)
            user.id = id

            return user
        }

        cursor?.close()
        db.close()
        return null
    }
}
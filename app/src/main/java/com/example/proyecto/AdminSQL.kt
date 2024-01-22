package com.example.proyecto

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class AdminSQL(context: Context, name: String, factory: CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {
    val TABLE_NAME= "Scores"
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table "+TABLE_NAME+" (user text primary key, points integer)")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists "+ TABLE_NAME)
        onCreate(db)
    }
}

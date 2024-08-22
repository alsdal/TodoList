package com.example.todopractice

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "testdb", null, 1) {
    // 데이터 저장 위한 테이블 준비
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table TODO_TB(" +
                "_id integer primary key autoincrement," +
                "todo not null)"
        )
    }
    // 버전 바뀔때, 스키마 변경 목적으로
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}
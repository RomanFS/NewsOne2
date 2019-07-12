package com.example.newsone2.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.newsone2.domain.NewsObject

private const val TAG = "DataHolder"

class DataHolder(context: Context, name: String?,
                  factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val tableData = "(_ID INTEGER PRIMARY KEY NOT NULL, url TEXT, imageUrl TEXT, " +
                "title TEXT, descrip TEXT, byline TEXT, copyright TEXT, date TEXT, source TEXT, favState BIT)"

        val CREATE_EMAILED_TABLE = ("CREATE TABLE $TABLE_EMAILED$tableData")
        val CREATE_VIEWED_TABLE = ("CREATE TABLE $TABLE_VIEWED$tableData")
        val CREATE_SHARED_TABLE = ("CREATE TABLE $TABLE_SHARED$tableData")
        val CREATE_FAVOURITE_TABLE = ("CREATE TABLE $TABLE_FAV$tableData")

        try {
            db.execSQL(CREATE_EMAILED_TABLE)
            db.execSQL(CREATE_VIEWED_TABLE)
            db.execSQL(CREATE_SHARED_TABLE)
            db.execSQL(CREATE_FAVOURITE_TABLE)
        } catch (e: Throwable) {
            Log.e(TAG, "DataCreate: ", e)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {
        try {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_EMAILED")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_VIEWED")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_SHARED")
            db.execSQL("DROP TABLE IF EXISTS $TABLE_FAV")
            onCreate(db)
        } catch (e: Throwable) {
            Log.e(TAG, "DataUpdate: ", e)
        }
    }

    fun addNews(tableName: String, newsList: ArrayList<NewsObject>) {
        val db = this.writableDatabase
        try {
            for (i in 0 until newsList.size) {
                val news = newsList[i]
                val values = ContentValues()

                values.put(url, news.url)
                values.put(title, news.title)
                values.put(descrip, news.descrip)
                values.put(copyright, news.copyright)
                values.put(imageUrl, news.imageUrl)
                values.put(source, news.source)
                values.put(date, news.date)
                values.put(byline, news.byline)
                values.put(favState, news.favState)

                db.insert(tableName, null, values)
            }
        } catch (e: Throwable) {
            Log.e(TAG, "AddNews: ", e)
        }
    }

    fun updateNews(tableName: String, newsList: ArrayList<NewsObject>) {
        val db = this.writableDatabase
        val query = "SELECT * FROM $tableName"
        val cursor = db.rawQuery(query, null)

        try {
            while (cursor.moveToNext()) {
                val id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_ID")))
                db.delete(
                    tableName, "_ID = ?",
                    arrayOf(id.toString())
                )
            }

            addNews(tableName, newsList)
        } catch (e: Throwable){
            Log.e(TAG, "deleteFav: ", e)
        } finally {
            cursor.close()
        }
    }

    fun findNews(tableName: String): ArrayList<NewsObject> {
        val query = "SELECT * FROM $tableName"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        val newsList = ArrayList<NewsObject>()

        try {
            while (cursor.moveToNext()) {
                val id = Integer.parseInt(cursor.getString(0))
                val url = cursor.getString(1)
                val title = cursor.getString(2)
                val descrip = cursor.getString(3)
                val copyright = cursor.getString(4)
                val imageUrl = cursor.getString(5)
                val source = cursor.getString(6)
                val date = cursor.getString(7)
                val byline = cursor.getString(8)
                val favState = cursor.getInt(9) > 0

                val news = NewsObject(id, url, imageUrl, title, descrip, byline, copyright, date, source, favState)

                newsList.add(news)
            }
        } catch (e: Throwable) {
            Log.e(TAG, "AddNews: ", e)
        } finally {
            cursor.close()
            return newsList
        }
    }

    fun addToFav(newsUrl: String) {
        val db = this.writableDatabase

        try {
            var query = "UPDATE $TABLE_EMAILED SET favState = '1' WHERE url = \"$newsUrl\""
            db.execSQL(query)
            query = "UPDATE $TABLE_SHARED SET favState = '1' WHERE url = \"$newsUrl\""
            db.execSQL(query)
            query = "UPDATE $TABLE_VIEWED SET favState = '1' WHERE url = \"$newsUrl\""
            db.execSQL(query)
        } catch (e: Throwable){
            Log.e(TAG, "deleteFav: ", e)
        }
    }

    fun deleteFav(tableName: String, newsUrl: String) {
        val db = this.writableDatabase
        var query = "SELECT $TABLE_FAV WHERE url = \"$newsUrl\""
        val cursor = db.rawQuery(query, null)

        try {
            if (cursor.moveToFirst()) {
                val id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_ID")))
                db.delete(
                    tableName, "_ID = ?",
                    arrayOf(id.toString())
                )
            }
            query = "UPDATE $TABLE_EMAILED SET favState = '0' WHERE url = \"$newsUrl\""
            db.execSQL(query)
            query = "UPDATE $TABLE_SHARED SET favState = '0' WHERE url = \"$newsUrl\""
            db.execSQL(query)
            query = "UPDATE $TABLE_VIEWED SET favState = '0' WHERE url = \"$newsUrl\""
            db.execSQL(query)
        } catch (e: Throwable){
            Log.e(TAG, "deleteFav: ", e)
        } finally {
            cursor.close()
        }
    }

    fun getItemCount(tableName: String): Int {
        val db = this.writableDatabase
        val query = "SELECT * FROM $tableName ORDER BY _ID DESC LIMIT 1"
        val cursor = db.rawQuery(query, null)
        var count = 0

        try {
            cursor.use {
                if (it.moveToFirst()) { count = it.getInt(0)}
            }
        } catch (e: Throwable) {
            Log.e(TAG, "getItemCount: ", e)
        } finally {
            cursor.close()
            return count
        }
    }

    companion object {
        private const val COLUMN_ID = "_ID"
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "newsData.db"
        const val TABLE_EMAILED = "emailed"
        const val TABLE_VIEWED = "viewed"
        const val TABLE_SHARED = "shared"
        const val TABLE_FAV = "fav"
        const val url = "url"
        const val title = "title"
        const val descrip = "descrip"
        const val copyright = "copyright"
        const val imageUrl = "imageUrl"
        const val source = "source"
        const val date = "date"
        const val byline = "byline"
        const val favState = "favState"
    }
}

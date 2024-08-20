package com.example.job.classes

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.net.URLEncoder
import kotlin.math.absoluteValue

class Query {
    private var select = ""
    private var from = "";
    private var where = "";
    private var join = "";
    private var group = "";
    private var orderBy = "";
    private var limit = "";
    private var offset = "";
    private var sql = "";

    fun select(s:Array<String>){
        this.select = s.joinToString(",")
        println("this.select = ${this.select}")
    }

    fun selectCustom(s:String){
        this.select = s
    }

    fun from(s:Array<String>){
        this.from = s.joinToString(",")
    }

    fun fromCustom(s:String){
        this.from = s
    }

    fun where(s:Array<String>){
        var column:String = ""
        var value:String = ""
        var sign:String = ""
        if (s.size == 3){
            column = s[0]
            value = s[1]
            sign = s[2]
            this.where += " WHERE '${column}'${sign}'${value}'"
        }
    }

    fun whereAnd(s:Array<String>){
        var column:String = ""
        var value:String = ""
        var sign:String = ""
        if (s.size == 3){
            column = s[0]
            value = s[1]
            sign = s[2]
            this.where += " AND '${column}'${sign}'${value}'"
        }
    }

    fun whereOr(s:Array<String>){
        var column:String = ""
        var value:String = ""
        var sign:String = ""
        if (s.size == 3){
            column = s[0]
            value = s[1]
            sign = s[2]
            this.where += " OR '${column}'${sign}'${value}'"
        }
    }

    fun whereCustom(s:String){
        this.where = "WHERE ${s}"
    }

    //fun onceWhere(){
    //    var r = trim(substr($this->where, 5, strlen($this->where)));
    //    //echo "<code>\$right = $right</code><br />";
    //    $this->where = "WHERE ($right)";
    //}

    fun joinCustom(s:String){
        this.join = s
    }

    fun orderBy(fields:Array<String>, sf:String){
        this.orderBy = "ORDER BY " + fields.joinToString(",") + " " + sf
    }

    fun limit(s:Array<Int>){
        var limit:Int = 0
        var offset:Int = 0
        if (s.size > 0){
            limit = s[0]
            if (s.size >= 2) offset = s[1]
        }
        this.limit = "LIMIT ${limit}"
        if (offset > 0) this.limit += ", ${offset}"
    }

    fun all():String{
        this.sql = "SELECT ${this.select} FROM ${this.from} ${this.join} ${this.where} ${this.orderBy} ${this.limit}"
        return this.sql
    }

    fun getRecs(mdb: SQLiteDatabase, sql: String): HashMap<Int, HashMap<String, kotlin.Any>>{
        val res: HashMap<Int, HashMap<String, kotlin.Any>> = HashMap()

        try {
            val myCursor: Cursor = mdb.rawQuery(sql, null)
            var i = 0
            while (myCursor.moveToNext()) {
                val items: HashMap<String, kotlin.Any> = HashMap()
                for (j in 0..myCursor.columnCount-1) {
                    items.put(myCursor.getColumnName(j), myCursor.getString(myCursor.getColumnIndex(myCursor.getColumnName(j)).absoluteValue))
                }
                res[i] = items
                ++i
            }
            myCursor.close()
            //mdb.close()
        }
        catch(e:Exception) {
            println(e.message.toString())
        }
        return res
    }
}
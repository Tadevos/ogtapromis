package com.example.job

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.job.classes.Query

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bt = findViewById<Button>(R.id.bt1)
        val tv = findViewById<TextView>(R.id.tv)

        bt.setOnClickListener(){
            var obj = Query()
            obj.select(arrayOf("id", "name"))
            //obj.from(arrayOf("table1"))
            obj.fromCustom("(SELECT * FROM [table2])")
            obj.orderBy(arrayOf("name", "price"), "ASC")
            obj.limit(arrayOf(10, 3))
            val sql:String = obj.all()
            tv.setText(sql)
            //obj.getRecs(mdb, sql)
        }
    }
}

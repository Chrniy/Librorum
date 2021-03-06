package com.dev.librorum.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import com.dev.librorum.R
import com.dev.librorum.utils.EXTRA_ID
import com.dev.librorum.customViews.RecyclerSorted
import com.dev.librorum.customViews.SimpleDividerItemDecoration
import com.dev.librorum.data.DBWrapper

class Sorted : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sorted)
        try {
            lateinit var adapter: RecyclerSorted
            val db = DBWrapper.getInstance(this)
            val dataList = db.listLikes()
            val textSorted = findViewById<TextView>(R.id.textSorted)
            val sortedList = findViewById<RecyclerView>(R.id.sortedList)

            if (dataList.size == 0)
                textSorted.text = "Ваш список желаемого пуст"
            else
                textSorted.text = ""

            adapter = RecyclerSorted(this, dataList) { bookData ->

                val intent = Intent(this@Sorted, BookInfo::class.java)
                intent.putExtra(EXTRA_ID, bookData._id.toString())
                startActivity(intent)

            }

            sortedList.adapter = adapter
            val layoutManager = LinearLayoutManager(this)
            sortedList.layoutManager = layoutManager
            sortedList.setHasFixedSize(true)
            sortedList.addItemDecoration(SimpleDividerItemDecoration(
                    getApplicationContext()
            ))

        } catch (e: Exception) {

            Log.d("Librorum", e.toString())
            startActivity(Intent(this@Sorted, Sorted::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            lateinit var adapter: RecyclerSorted
            val db = DBWrapper.getInstance(this)
            val dataList = db.listLikes()
            val textSorted = findViewById<TextView>(R.id.textSorted)
            val sortedList = findViewById<RecyclerView>(R.id.sortedList)

            if (dataList.size == 0)
                textSorted.text = "Ваш список желаемого пуст"
            else
                textSorted.text = ""

            adapter = RecyclerSorted(this, dataList) { bookData ->

                val intent = Intent(this@Sorted, BookInfo::class.java)
                intent.putExtra(EXTRA_ID, bookData._id.toString())
                startActivity(intent)

            }

            sortedList.adapter = adapter
            val layoutManager = LinearLayoutManager(this)
            sortedList.layoutManager = layoutManager
            sortedList.setHasFixedSize(true)
            sortedList.addItemDecoration(SimpleDividerItemDecoration(
                    getApplicationContext()
            ))

        } catch (e: Exception) {

            Log.d("Librorum", e.toString())
            startActivity(Intent(this@Sorted, Sorted::class.java))
        }
    }
}

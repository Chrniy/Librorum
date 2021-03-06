package com.dev.librorum.activities

import android.content.Intent
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.dev.librorum.R
import com.dev.librorum.utils.HEIGHT
import com.dev.librorum.data.DBWrapper
import org.jetbrains.anko.doAsync
import java.io.BufferedReader
import java.io.InputStreamReader
import com.dev.librorum.utils.Prefs
import com.dev.librorum.utils.WIDTH
import com.dev.librorum.data.DBCWrapper


class Loading : AppCompatActivity(), DBWrapper.DbInteraction {
    private var flag: Boolean = false

    private fun changename (text: String){
        val name = findViewById(R.id.loadingText) as TextView
                name.text = text
    }

    override fun onFileReaded() {
        flag = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        try {
            val prefs = Prefs(this)
            val inputStream = resources.openRawResource(R.raw.loading)
            var line = BufferedReader(InputStreamReader(inputStream)).readLines()
            val imageLoading = findViewById<ImageView>(R.id.imageLoading)
            val loadingText = findViewById<TextView>(R.id.loadingText)
            var number = 1
            val tap = findViewById<ConstraintLayout>(R.id.nextText)
            val display = windowManager.defaultDisplay
            val size = Point()
            val skip = findViewById<Button>(R.id.buttonSkip)
            display.getSize(size)
            val width = size.x
            val height = size.y
            var originNumber = line.size
            HEIGHT = height
            WIDTH = width
            loadingText.maxWidth = (width * (0.9)).toInt()

            if (prefs.readLoading()) {
                line = line.dropLast(1)
            }

            imageLoading.layoutParams.width = (width*0.4).toInt()
            imageLoading.layoutParams.height = (height*0.4).toInt()

            if (prefs.firstTime() != true && prefs.readLoading() != true) {
                startActivity(Intent(this@Loading, MainActivity::class.java))
                prefs.finishedReadingLoading()
            }

            if (prefs.readLoading() != true) {
                doAsync {
                    DBCWrapper.initDb(applicationContext, resources)
                    DBWrapper.registerCallback(this@Loading, "Loading")
                    DBWrapper.readFile(applicationContext, resources)

                    if (number >= line.size) {
                        startActivity(Intent(this@Loading, MainActivity::class.java))

                        prefs.finishedReadingLoading()
                        prefs.notFirstTime()
                    }
                }
            }

            tap.setOnClickListener {
                if (number < line.size) {
                    changename(line[number])
                    number++
                } else if (flag == true || prefs.readLoading() == true) {

                    startActivity(Intent(this@Loading, MainActivity::class.java))

                    prefs.finishedReadingLoading()
                    prefs.notFirstTime()
                }

                if(number == 3 ||number == 4 ||number == 14 ) {
                    imageLoading.setImageResource(R.drawable.p257)
                    imageLoading.visibility = View.VISIBLE
                }
                else if (number == 5 ||number == 6 ||number == 7) {
                    imageLoading.setImageResource(R.drawable.p967)
                    imageLoading.visibility = View.VISIBLE
                }
                else if (number == 8) {
                    imageLoading.setImageResource(R.drawable.p970)
                    imageLoading.visibility = View.VISIBLE
                }
                else if (number == 9 || number == 10) {
                    imageLoading.setImageResource(R.drawable.p979)
                    imageLoading.visibility = View.VISIBLE
                }
                else if (number == 11 ||number == 12 ||number == 13){
                    imageLoading.setImageResource(R.drawable.p451)
                    imageLoading.visibility = View.VISIBLE
                    }
                else if(number == 1 ||number == 2 ||number >= 15)
                    imageLoading.visibility = View.INVISIBLE
            }

            skip.setOnClickListener {
                number = line.size - 1
                changename(line[number])

                if (originNumber == line.size)
                    imageLoading.visibility = View.INVISIBLE

                else {

                    imageLoading.setImageResource(R.drawable.p257)
                    imageLoading.visibility = View.VISIBLE

                }

            }
            changename(line[number - 1])
        }
        catch (e: Exception){
            Log.d("Librorum", e.toString())
            startActivity(Intent(this@Loading, Loading::class.java))
        }
    }


}

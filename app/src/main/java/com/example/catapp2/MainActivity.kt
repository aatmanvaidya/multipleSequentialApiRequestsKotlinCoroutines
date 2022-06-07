package com.example.catapp2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                getCurrentData()
            }
        }
    }

    object RetrofitHelper {

        val baseUrl = "https://cat-fact.herokuapp.com"

        fun getInstance(): Retrofit {
            return Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    private suspend fun getCurrentData() {
        try {
            withContext(Dispatchers.Main) {
                val job1 = async { workOfAPICall1() }.await()
                val job2 = async { workOfAPICall2() }.await()
                val job3 = async { workOfAPICall3() }.await()
            }
        } catch (e: Exception) {
            e.message?.let { Log.d("MainActivity", it) }
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun workOfAPICall1(): Unit {
        try {
            val text: TextView = findViewById(R.id.text)
            val progressBar = findViewById<ProgressBar>(R.id.progressBar)

            text.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            val api = MainActivity.RetrofitHelper.getInstance().create(ApiRequests::class.java)
            val result = api.getCatFacts()
            Log.d("MainActivity", result.toString())
            if (result != null) {
                val data = result.body()!!
                Log.d("MainActivity", data.toString())
                withContext(Dispatchers.Main) {
                    text.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    text.text = data.text
                }
            }
        } catch (e: Exception) {
            e.message?.let { Log.d("MainActivity", it) }
            val newText = "ERORRR://"
            text.text = newText
            withContext(Dispatchers.Main) {
                text.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }

    private suspend fun workOfAPICall2(): Unit {
        try {
            val text2: TextView = findViewById(R.id.text2)
            val progressBar2 = findViewById<ProgressBar>(R.id.progressBar2)
            text2.visibility = View.GONE
            progressBar2.visibility = View.VISIBLE
            val api = MainActivity.RetrofitHelper.getInstance().create(ApiRequests::class.java)
            val result2 = api.getCatFacts()
            if (result2 != null) {
                val data = result2.body()!!
                Log.d("MainActivity", data.toString())
                withContext(Dispatchers.Main) {
                    text2.visibility = View.VISIBLE
                    progressBar2.visibility = View.GONE
                    text2.text = data.text
                }
            }
        } catch (e: Exception) {
            e.message?.let { Log.d("MainActivity", it) }
            val newText = "ERORRR://"
            text2.text = newText
            withContext(Dispatchers.Main) {
                text2.visibility = View.VISIBLE
                progressBar2.visibility = View.GONE
            }
        }

    }

    private suspend fun workOfAPICall3() {
        try {
            val text3: TextView = findViewById(R.id.text3)
            val progressBar3 = findViewById<ProgressBar>(R.id.progressBar3)
            text3.visibility = View.GONE
            progressBar3.visibility = View.VISIBLE
            val api = MainActivity.RetrofitHelper.getInstance().create(ApiRequests::class.java)
            val result3 = api.getCatFacts()
            if (result3 != null) {
                val data = result3.body()!!
                Log.d("MainActivity", data.toString())
                withContext(Dispatchers.Main) {
                    text3.visibility = View.VISIBLE
                    progressBar3.visibility = View.GONE
                    text3.text = data.text
                }
            }
        } catch (e: Exception) {
            e.message?.let { Log.d("MainActivity", it) }
            val newText = "ERORRR://"
            text3.text = newText
            withContext(Dispatchers.Main) {
                text3.visibility = View.VISIBLE
                progressBar3.visibility = View.GONE
            }
        }
    }
}

/* A pre version of it for backup
private fun getCurrentData() {
        val text: TextView = findViewById(R.id.text)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        text.visibility = View.GONE
        progressBar.visibility = View.VISIBLE

        val text2: TextView = findViewById(R.id.text2)
        val progressBar2 = findViewById<ProgressBar>(R.id.progressBar2)

        text2.visibility = View.GONE
        progressBar2.visibility = View.VISIBLE

        val api = MainActivity.RetrofitHelper.getInstance().create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result = async { api.getCatFacts() }.await()
                if (result != null) {

                    val data = result.body()!!
                    Log.d("MainActivity", data.toString())

                    withContext(Dispatchers.Main) {
                        text.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        text.text = data.text
                    }

                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        applicationContext,
                        "BRUHH:////",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }



        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result2 = async { api.getCatFacts() }.await()
                if (result2 != null) {

                    val data = result2.body()!!
                    Log.d("MainActivity", data.toString())
                    withContext(Dispatchers.Main) {
                        text2.visibility = View.VISIBLE
                        progressBar2.visibility = View.GONE
                        text2.text = data.text
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        applicationContext,
                        "BRUHH:////",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

 */

/*
Version 2
private suspend fun getCurrentData() {
        val text: TextView = findViewById(R.id.text)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        text.visibility = View.GONE
        progressBar.visibility = View.VISIBLE

        val text2: TextView = findViewById(R.id.text2)
        val progressBar2 = findViewById<ProgressBar>(R.id.progressBar2)
        text2.visibility = View.GONE
        progressBar2.visibility = View.VISIBLE

        val api = MainActivity.RetrofitHelper.getInstance().create(ApiRequests::class.java)
        try {
            withContext(IO) {
                val result = withContext(Dispatchers.Default) { api.getCatFacts() }
                if (result != null) {

                    val data = result.body()!!
                    Log.d("MainActivity", data.toString())

                    withContext(Dispatchers.Main) {
                        text.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        text.text = data.text
                    }

                }
                val result2 = withContext(Dispatchers.Default) { api.getCatFacts() }
                if (result2 != null) {

                    val data2 = result2.body()!!
                    Log.d("MainActivity", data2.toString())
                    withContext(Dispatchers.Main) {
                        text2.visibility = View.VISIBLE
                        progressBar2.visibility = View.GONE
                        text2.text = data2.text
                    }

                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    applicationContext,
                    "BRUHH:////",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

 */
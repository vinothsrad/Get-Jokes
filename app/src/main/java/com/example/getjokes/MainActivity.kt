package com.example.getjokes

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.getjokes.api.ApiInterface
import com.example.getjokes.api.RestClient
import com.example.getjokes.response.jokeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var btn_jokes: Button
    private lateinit var tv_jokes: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var mApiService: ApiInterface

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_jokes=findViewById(R.id.btnJokes)
        tv_jokes=findViewById(R.id.textjokes)
        progressBar=findViewById(R.id.progressBar)
        mApiService = RestClient.client.create(ApiInterface::class.java)


        btn_jokes.setOnClickListener {
           progressBar.visibility = View.VISIBLE

            getjokes(this){ jokes ->
                tv_jokes.text= jokes.value
                progressBar.visibility=View.GONE
            }

        }

    }

    private fun getjokes(context: MainActivity,callback: (jokeResponse) -> Unit) {
        val call = mApiService!!.getjokes("android")
        call.enqueue(object : Callback<jokeResponse>{
            override fun onResponse(
                call: Call<jokeResponse>,
                response: Response<jokeResponse>
            ) {
                Log.d(TAG,"jokes :" + response.body()!!.categories.size)
                val jokes = response.body()
                if (jokes != null)
                {
                    val joke: jokeResponse= response.body() as jokeResponse
                    callback(joke)
                }
            }

            override fun onFailure(call: Call<jokeResponse>, t: Throwable) {
                Toast.makeText(context, "Request Fail", Toast.LENGTH_SHORT).show()
            }

        })

    }




}
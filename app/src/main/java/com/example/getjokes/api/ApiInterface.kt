package com.example.getjokes.api

import com.example.getjokes.response.jokeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("jokes/random")
    fun getjokes(@Query("tagged") tags: String):Call<jokeResponse>
}
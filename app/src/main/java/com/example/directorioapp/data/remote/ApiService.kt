package com.example.directorioapp.data.remote

import com.example.directorioapp.data.model.Post
import com.example.directorioapp.data.model.User
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("posts")
    suspend fun getUserPosts(@Query("userId") userId: Int): List<Post>

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}

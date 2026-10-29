package com.example.directorioapp.data.model

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("userId") val userId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
) {
    val displayTitle: String
        get() = PostTranslator.translateTitle(id, title)

    val displayBody: String
        get() = PostTranslator.translateBody(id, body)
}

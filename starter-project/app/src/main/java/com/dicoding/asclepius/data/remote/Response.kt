package com.dicoding.asclepius.data.remote

import com.google.gson.annotations.SerializedName

// Data model representing the response from the news API
// It could be generated using GeneratePojo class from JSON response
data class Response(
    @field:SerializedName("totalResults")
    val totalResults: Int,                // Total number of results available

    @field:SerializedName("articles")
    val articles: List<ArticlesItem>,     // List of articles in the response

    @field:SerializedName("status")
    val status: String                    // Response status (e.g., "ok")
)

// Data model for each article item in the response
data class ArticlesItem(
    @field:SerializedName("publishedAt")
    val publishedAt: String,              // Article publication date

    @field:SerializedName("author")
    val author: String,                   // Article author

    @field:SerializedName("urlToImage")
    val urlToImage: String,               // URL to the article's image

    @field:SerializedName("description")
    val description: String,              // Article description

    @field:SerializedName("source")
    val source: Source,                   // Source of the article

    @field:SerializedName("title")
    val title: String,                    // Article title

    @field:SerializedName("url")
    val url: String,                      // URL to the article

    @field:SerializedName("content")
    val content: String                   // Article content
)

// Data model for the source of an article
data class Source(
    @field:SerializedName("name")
    val name: String,                     // Source name

    @field:SerializedName("id")
    val id: String                        // Source ID
)

package com.example.model

data class Review(
    val id: String,
    val authorName: String,
    val authorAvatarColor: Long,
    val rating: Int,
    val date: String,
    val comment: String,
    val helpfulCount: Int,
    val userUpvoted: Boolean = false
)

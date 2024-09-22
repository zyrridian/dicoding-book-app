package com.example.bookapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val title: String,
    val author: String,
    val publishedYear: String,
    val genre: String,
    val description: String,
    val isbn: String,
    val pages: String,
    val coverImage: String,
    val linkToPurchase: String,
    val ratings: String,
) : Parcelable
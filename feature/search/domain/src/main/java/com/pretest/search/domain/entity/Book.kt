package com.pretest.search.domain.entity

data class Book(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val price: Long = 0L,
    val publishDate: String = "",
    val authors: List<Author> = emptyList(),
    val like: Boolean = false
)
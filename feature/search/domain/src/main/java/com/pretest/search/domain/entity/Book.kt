package com.pretest.search.domain.entity

import java.io.Serializable

data class Book(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val price: Long = 0L,
    val publishDate: String = "",
    val authors: List<Author> = emptyList(),
    val publisher: String = "",
    val like: Boolean = false
): Serializable
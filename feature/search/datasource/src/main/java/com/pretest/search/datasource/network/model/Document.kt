package com.pretest.search.datasource.network.model

import com.google.gson.annotations.SerializedName

data class Document(
    @SerializedName("authors") val authors: List<String> = emptyList(),
    @SerializedName("contents") val contents: String = "",
    @SerializedName("datetime") val datetime: String = "",
    @SerializedName("isbn") val isbn: String = "",
    @SerializedName("price") val price: Long = 0L,
    @SerializedName("publisher") val publisher: String = "",
    @SerializedName("sale_price") val salePrice: Long = 0L,
    @SerializedName("status") val status: String = "",
    @SerializedName("thumbnail") val thumbnail: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("translators") val translators: List<String> = emptyList(),
    @SerializedName("url") val url: String = ""
)
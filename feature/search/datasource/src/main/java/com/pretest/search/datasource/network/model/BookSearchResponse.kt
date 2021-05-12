package com.pretest.search.datasource.network.model

import com.google.gson.annotations.SerializedName

data class BookSearchResponse(
    @SerializedName("documents") val documents: List<Document>,
    @SerializedName("meta") val metaData: MetaData
)
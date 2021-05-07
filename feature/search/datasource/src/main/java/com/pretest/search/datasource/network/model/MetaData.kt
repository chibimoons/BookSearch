package com.pretest.search.datasource.network.model

import com.google.gson.annotations.SerializedName

data class MetaData(
    @SerializedName("is_end") val isEnd: Boolean = true,
    @SerializedName("pageable_count") val pageableCount: Long = 0,
    @SerializedName("total_count") val totalCount: Long = 0
)
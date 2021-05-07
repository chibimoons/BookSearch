package com.pretest.search.main


import com.pretest.network.buildHttpClient
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking

import org.junit.Test

class HttpClientUtilsTest {
    @Test
    fun useHttpClient() {
        runBlocking {
            val httpClient = buildHttpClient()
            var response: TestResponse? = null
            try {
                response = httpClient.get<TestResponse> {
                    headers.append("Authorization", "KakaoAK 034579f787e6c8a61801506702ac6002")
                    url("https://dapi.kakao.com/v3/search/book?target=title")
                    parameter("query", "미움받을 용기")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            assert(response?.metaData != null)
        }
    }
}
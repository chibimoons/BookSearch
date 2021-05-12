package com.pretest.network

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*

fun buildHttpClient() = HttpClient {
    install(HttpTimeout)
    {
        requestTimeoutMillis = 3000
        connectTimeoutMillis = 3000
    }
    install(JsonFeature) {
        serializer = GsonSerializer {
            setPrettyPrinting()
            disableHtmlEscaping()
        }
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println("Logger Ktor =>${message}")
            }

        }
        level = LogLevel.ALL
    }

    install(ResponseObserver) {
        onResponse { response ->
            println("HTTP status:${response.status.value}")
        }
    }
}

package com.pretest.network

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*

// Todo: 커스텀 로거 주입 받을 수 있게

fun buildHttpClient() = HttpClient() {
    install(JsonFeature) {
        serializer = GsonSerializer() {
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

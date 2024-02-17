package com.ihfazh.dailytrackerchild_parent

import android.app.Application
import android.content.Context
import com.ihfazh.dailytrackerchild_parent.remote.ActualClient
import com.ihfazh.dailytrackerchild_parent.remote.TokenHeader
import com.ihfazh.dailytrackerchild_parent.utils.ChildrenCache
import com.ihfazh.dailytrackerchild_parent.utils.TokenCache
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ActivityCompositionRoot(context: Application) {
    val childrenCache: ChildrenCache = ChildrenCache(context.getSharedPreferences("childrenCache", Context.MODE_PRIVATE))
    val tokenCacheUtil = TokenCache(context.getSharedPreferences("tokenCache", Context.MODE_PRIVATE))

//    val dateProvider = DateProvider()


    private val ktor = HttpClient(OkHttp){

        install(HttpTimeout){
            socketTimeoutMillis = 120_000
        }

        install(ContentNegotiation){
            json(Json{
                ignoreUnknownKeys = true
                encodeDefaults = true
                isLenient = true
                allowSpecialFloatingPointValues = true
                allowStructuredMapKeys = true
                prettyPrint = false
                useArrayPolymorphism = false
            })
        }

        install(TokenHeader){
            tokenCache = tokenCacheUtil
        }
    }

    val client = ActualClient(ktor, tokenCacheUtil)
//    val client: DummyClient = DummyClient()

}
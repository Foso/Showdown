package de.jensklingenberg.showdown.server.common

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import de.jensklingenberg.showdown.model.Response
import de.jensklingenberg.showdown.model.WebsocketResource
import java.io.IOException
import java.lang.reflect.ParameterizedType


fun Moshi.toJson(list: List<Any>): String {
    val myData: ParameterizedType =
        Types.newParameterizedType(List::class.java, Any::class.java)
    val adapter = this.adapter<kotlin.collections.List<Any>>(myData)
    return adapter?.toJson(list) ?: ""
}

inline fun <reified T> Moshi.toJson(any: WebsocketResource<T>): String {
    val parameterizedType =
        Types.newParameterizedType(WebsocketResource::class.java, T::class.java)

    val jsonAdapter = this.adapter<WebsocketResource<T>>(parameterizedType)
    return jsonAdapter?.toJson(any) ?: ""
}

fun Moshi.toJson(any: Any): String {
    val jsonAdapter = this.adapter(Any::class.java)
    return jsonAdapter?.toJson(any) ?: ""
}


inline fun <reified T> fromJson(json:String) : T?{
    val moshi = Moshi.Builder().build()
    val jsonAdapter: JsonAdapter<T> = moshi.adapter(T::class.java)

    return try {
        jsonAdapter.failOnUnknown().fromJson(json)
    } catch (io: IOException) {
        null
    }catch (jsonDataException: JsonDataException){
        null
    }
}

inline fun <reified T> WebsocketResource<T>.toJson(): String {
    val moshi = Moshi.Builder().build()
    val parameterizedType =
        Types.newParameterizedType(WebsocketResource::class.java, T::class.java)
    val adapter = moshi.adapter<WebsocketResource<T>>(parameterizedType)
    return adapter.toJson(this)?:""
}
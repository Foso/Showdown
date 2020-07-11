package de.jensklingenberg.showdown.server.game

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException
import java.lang.reflect.ParameterizedType


fun Moshi.toJson(list: List<Any>): String {
    val myData: ParameterizedType =
        Types.newParameterizedType(List::class.java, Any::class.java)
    val adapter = this.adapter<kotlin.collections.List<Any>>(myData)
    return adapter?.toJson(list) ?: ""
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
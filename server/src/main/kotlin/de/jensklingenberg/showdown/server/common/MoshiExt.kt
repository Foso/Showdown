package de.jensklingenberg.showdown.server.common

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.IOException
import java.lang.reflect.ParameterizedType


fun Moshi.toJson(list: List<Any>): String {
    val myData: ParameterizedType =
        Types.newParameterizedType(List::class.java, Any::class.java)
    val adapter = this.adapter<List<Any>>(myData)
    return adapter?.toJson(list) ?: ""
}


fun Moshi.toJson(any: Any): String {
    return try {
        val jsonAdapter = this.adapter(Any::class.java)
        jsonAdapter?.toJson(any) ?: ""
    } catch (ex: Exception) {
        ""
    }

}


inline fun <reified T> fromJson(json: String): T? {
    //TODO clean json
    val js = json.replace("\"_", "\"").replace("_0", "").replace("_1", "")
    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val jsonAdapter: JsonAdapter<T> = moshi.adapter(T::class.java)

    return try {
        jsonAdapter.failOnUnknown().fromJson(js)
    } catch (io: IOException) {
        null
    } catch (jsonDataException: JsonDataException) {
        null
    }
}

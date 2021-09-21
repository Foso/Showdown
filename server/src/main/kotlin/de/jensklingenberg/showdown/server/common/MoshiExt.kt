package de.jensklingenberg.showdown.server.common

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
    return try {
        val jsonAdapter = this.adapter(Any::class.java)
        jsonAdapter?.toJson(any) ?: ""
    }catch (ex: Exception){
        ""
    }

}


inline fun <reified T> fromJson(json:String) : T?{
    //TODO clean json
    val js = json.replace("\"_","\"").replace("_0","")
    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val jsonAdapter: JsonAdapter<T> = moshi.adapter(T::class.java)

    return try {
        jsonAdapter.failOnUnknown().fromJson(js)
    } catch (io: IOException) {
        null
    }catch (jsonDataException: JsonDataException){
        null
    }
}

inline fun <reified T> WebsocketResource<T>.toJson(): String {
   try {
       val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

       val parameterizedType =
           Types.newParameterizedType(WebsocketResource::class.java, T::class.java)
       val adapter = moshi.adapter<WebsocketResource<T>>(parameterizedType)
       return adapter.toJson(this)?:""
   }catch (ex:Exception){
       println("Exception: $ex")
       return ""
   }
}
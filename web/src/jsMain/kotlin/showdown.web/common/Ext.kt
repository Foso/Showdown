package showdown.web.common

fun Any.stringify(): String {
    return JSON.stringify(this)
}
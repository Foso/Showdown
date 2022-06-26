package de.jensklingenberg.mealapp

sealed class Page {
    object Main : Page()
    class Detail(val mealId: Int) : Page()
}
package de.jensklingenberg.mealapp.mainpage

import androidx.compose.runtime.*
import de.jensklingenberg.mealapp.common.JKRaisedButton

import dev.petuska.kmdc.textfield.MDCTextField
import dev.petuska.kmdc.textfield.icon.MDCTextFieldTrailingIcon
import dev.petuska.kmdcx.icons.MDCIcon
import dev.petuska.kmdcx.icons.mdcIcon
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLDivElement

@Composable
fun Search(mainPageViewModel: MainPageViewModel) {
    var mytext: String by remember { mutableStateOf("") }
    Div(attrs = fun AttrsScope<HTMLDivElement>.() {
        style {
            width(100.percent)
            display(DisplayStyle.Flex)
            alignItems(AlignItems.Center)
            justifyContent(JustifyContent.Center)
        }
    }) {
        JKTextField(mytext, label = "Search", onTextChange =  {
            mytext = it
        }, onEnterPressed = {
            mainPageViewModel.onSearch(mytext)
        })
        JKRaisedButton("Search", onClick = {
            mainPageViewModel.onSearch(mytext)
        })
        Filter(mainPageViewModel.filters.value) {
            mainPageViewModel.onFilterSelected(it)
        }
    }
}


@Composable
fun JKTextField(value: String, label: String? = null, onTextChange: (String) -> Unit, onEnterPressed: ()->Unit) {
    MDCTextField(value = value, label = label, attrs = {
        onKeyDown {
            if(it.key == "Enter"){
                onEnterPressed()
            }
        }
        onInput { onTextChange(it.value) }
    }, trailingIcon = {
        if (value.isNotEmpty()) {
            MDCTextFieldTrailingIcon(clickable = true, attrs = {
                mdcIcon()
                onClick { onTextChange("") }
            }) { Text(MDCIcon.Search.type) }
        }
    })
}

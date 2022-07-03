package de.jensklingenberg.mealapp.mainpage

import androidx.compose.runtime.Composable
import dev.petuska.kmdc.textfield.MDCTextField


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

    })
}

package de.jensklingenberg.mealapp.mainpage

import androidx.compose.runtime.Composable
import dev.petuska.kmdc.textfield.MDCTextField
import dev.petuska.kmdc.textfield.icon.MDCTextFieldTrailingIcon
import dev.petuska.kmdcx.icons.MDCIcon
import dev.petuska.kmdcx.icons.mdcIcon
import org.jetbrains.compose.web.dom.Text


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

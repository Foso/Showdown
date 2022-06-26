package de.jensklingenberg.mealapp.common

import androidx.compose.runtime.Composable
import dev.petuska.kmdc.button.MDCButton
import dev.petuska.kmdc.button.MDCButtonType
import dev.petuska.kmdc.icon.button.Icon
import dev.petuska.kmdc.icon.button.MDCIconButton
import dev.petuska.kmdc.icon.button.onChange
import dev.petuska.kmdc.snackbar.Label
import dev.petuska.kmdc.snackbar.MDCSnackbar
import dev.petuska.kmdc.textfield.MDCTextField
import dev.petuska.kmdc.textfield.icon.MDCTextFieldTrailingIcon
import dev.petuska.kmdcx.icons.MDCIcon
import dev.petuska.kmdcx.icons.mdcIcon
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.css.CSSNumeric
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

@Composable
fun JKRaisedButton(text: String, onClick: () -> Unit) {
    MDCButton(text = text, type = MDCButtonType.Raised) {
        height("200px")
        onClick { onClick() }
    }
}


fun <TElement : HTMLElement> AttrsScope<TElement>.height(s: String) {
    attr("height", s)
}

@Composable
fun JKSnackbar(text: String) {
    MDCSnackbar(
        closeOnEscape = true,
        timeoutMs = 2000,
        open = true,
        attrs = {

        }
    ) {
        Label(text)

    }
}

@Composable
fun JKImage(src: String, height: CSSNumeric, alt: String = "") {
    Img(src, alt = alt, attrs = {
        style {
            height(height)
        }
    })
}


@Composable
fun HeightSpacer(value: CSSNumeric) {
    Div(attrs = {
        style {
            height(value)
        }
    }) {

    }
}

fun <T : HTMLElement> AttrsScope<T>.ariaDescribedBy(id: String) {
    attr("aria-describedby", id)
}


@Composable
fun IconButton(onIcon: MDCIcon, offIcon: MDCIcon = onIcon, onChange: () -> Unit) {
    MDCIconButton(true, attrs = {
        onChange { onChange() }
    }) {

        Icon(on = true, attrs = {

            mdcIcon()
        }) {
            Text(onIcon.type)
        }
        Icon(on = false, attrs = {

            mdcIcon()
        }) {
            Text(offIcon.type)
        }


    }
}

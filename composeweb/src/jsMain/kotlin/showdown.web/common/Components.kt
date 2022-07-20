package showdown.web.common

import androidx.compose.runtime.Composable
import dev.petuska.kmdc.button.Icon
import dev.petuska.kmdc.button.MDCButton
import dev.petuska.kmdc.button.MDCButtonIconType
import dev.petuska.kmdc.button.MDCButtonType
import dev.petuska.kmdc.icon.button.Icon
import dev.petuska.kmdc.icon.button.MDCIconButton
import dev.petuska.kmdc.icon.button.onChange
import dev.petuska.kmdc.snackbar.Action
import dev.petuska.kmdc.snackbar.Actions
import dev.petuska.kmdc.snackbar.Label
import dev.petuska.kmdc.snackbar.MDCSnackbar
import dev.petuska.kmdc.textfield.MDCTextField
import dev.petuska.kmdcx.icons.MDCIcon
import dev.petuska.kmdcx.icons.mdcIcon
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

@Composable
fun JKRaisedButton(text: String, leadingIcon: MDCIcon? = null, onClick: () -> Unit) {

    MDCButton(type = MDCButtonType.Raised, touch = true, icon = MDCButtonIconType.Leading, attrs = {

        style {
            backgroundColor(jkBLue)
        }
        onClick { onClick() }

    }) {
        Icon(attrs = { mdcIcon() }) { leadingIcon?.let { Text(it.type) } }
        Text(text)
    }
}


@Composable
fun ConnectionErrorSnackbar(text: String, onRetryClicked: () -> Unit) {
    MDCSnackbar(
        closeOnEscape = true,
        timeoutMs = null,
        open = true,
        attrs = {

        }
    ) {
        Label(text)
        Actions() {
            Action("Retry", attrs = {
                style {
                    color(Color.red)
                }
                onClick { onRetryClicked() }
            })

        }
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

@Composable
fun WidthSpacer(value: CSSNumeric) {
    Div(attrs = {
        style {
            width(value)
        }
    }) {

    }
}

fun <T : HTMLElement> AttrsScope<T>.ariaDescribedBy(id: String) {
    attr("aria-describedby", id)
}


@Composable
fun IconButton(
    onIcon: MDCIcon,
    offIcon: MDCIcon = onIcon,
    style: StyleScope.() -> Unit = {},
    onClick: () -> Unit = {},
    onChange: () -> Unit
) {
    MDCIconButton(true, attrs = {
        style {
            style()

        }
        onClick { onClick() }
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


@Composable
fun JKTextField(value: String, label: String? = null, onTextChange: (String) -> Unit, onEnterPressed: () -> Unit) {
    MDCTextField(value = value, label = label, attrs = {
        onKeyDown {
            if (it.key == "Enter") {
                onEnterPressed()
            }
        }
        onInput { onTextChange(it.value) }
    }, trailingIcon = {

    })
}

val jkBLue = rgb(63, 81, 181)

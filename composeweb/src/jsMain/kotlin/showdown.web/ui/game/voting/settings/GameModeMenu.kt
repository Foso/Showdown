package showdown.web.ui.game.voting.settings

import androidx.compose.runtime.*
import dev.petuska.kmdc.menu.MDCMenu
import dev.petuska.kmdc.menu.MenuItem
import dev.petuska.kmdc.menu.onSelected
import dev.petuska.kmdc.menu.surface.MDCMenuSurfaceAnchor
import dev.petuska.kmdc.menu.surface.onClosed
import dev.petuska.kmdc.textfield.MDCTextField
import dev.petuska.kmdc.textfield.MDCTextFieldType
import dev.petuska.kmdc.textfield.icon.MDCTextFieldTrailingIcon
import dev.petuska.kmdcx.icons.MDCIcon
import dev.petuska.kmdcx.icons.mdcIcon
import org.jetbrains.compose.web.dom.Text

@Composable
public fun <T> rememberMutableStateOf(initial: T): MutableState<T> = remember { mutableStateOf(initial) }

@Composable
fun DropdownMenu(label: String, menuItems: List<String>, onEntrySelected: (Int) -> Unit) {
    var open by rememberMutableStateOf(false)

    var selectedId by mutableStateOf<Int>(0)
    var input by rememberMutableStateOf(menuItems[selectedId])
    MDCMenuSurfaceAnchor(attrs = {
        style {
            property("width", "fit-content")
        }
    }) {
        MDCTextField(
            value = input,
            label = label,
            type = MDCTextFieldType.Outlined,
            attrs = {
                onClick { open = true }
                onInput {
                    input = it.value
                    open = true
                }

            },
            trailingIcon = {
                MDCTextFieldTrailingIcon(clickable = true, attrs = {
                    onClick { open = !open }
                    mdcIcon()
                }) {
                    Text(
                        if (open) {
                            MDCIcon.ArrowDropUp.type
                        } else {
                            MDCIcon.ArrowDropDown.type
                        }
                    )
                }
            }
        )

        MDCMenu(
            open = open,
            selectedId = selectedId,

            attrs = {

                onSelected {
                    selectedId = it.detail.index
                    onEntrySelected(selectedId)
                    input = menuItems[it.detail.index]
                    open = false
                }
                onClosed { open = false }

            }
        ) {
            menuItems.forEach {
                MenuItem(

                    activated = input.isNotBlank() && it.equals(input, ignoreCase = true)
                ) {
                    Text(it)
                }
            }
        }
    }
}
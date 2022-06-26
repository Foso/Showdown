package de.jensklingenberg.mealapp.mainpage

import androidx.compose.runtime.*
import de.jensklingenberg.mealapp.common.IconButton
import dev.petuska.kmdc.checkbox.MDCCheckbox
import dev.petuska.kmdc.menu.surface.MDCMenuSurface
import dev.petuska.kmdc.menu.surface.MDCMenuSurfaceAnchor
import dev.petuska.kmdcx.icons.MDCIcon
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLDivElement

data class Filter(val name: String, val checked: Boolean)


@Composable
fun Filter(countries: List<Filter>, onFilterSelected: (Int) -> Unit) {

    var openMenu: Boolean by remember { mutableStateOf(false) }
    IconButton(MDCIcon.FilterList) {
        openMenu = !openMenu
    }
    MDCMenuSurfaceAnchor(attrs = {
        style {
            width(10.percent)
            display(DisplayStyle.Flex)
            alignItems(AlignItems.Start)
        }
    }) {

        MDCMenuSurface(
            open = openMenu,
            fullWidth = true,
            restoreFocusOnClose = true
        ) {
            countries.forEachIndexed { index, filter ->
                Div(attrs = fun AttrsScope<HTMLDivElement>.() {
                    onClick {
                        onFilterSelected(index)
                        openMenu = false
                    }
                    style {
                        display(DisplayStyle.Flex)
                        justifyContent(JustifyContent.Center)
                        alignItems(AlignItems.Center)
                        property("width", "fit-content")
                    }
                }) {
                    MDCCheckbox(filter.checked)
                    Text(filter.name)
                }


            }

        }
    }


}
package de.jensklingenberg.mealapp.common

import androidx.compose.runtime.Composable
import dev.petuska.kmdc.drawer.*
import dev.petuska.kmdc.typography.MDCBody1
import org.jetbrains.compose.web.dom.Text

@Composable
fun Drawer(drawerOpened: Boolean) {
    var drawerOpened1 = drawerOpened
    MDCDrawer(open = drawerOpened1, type = MDCDrawerType.Modal, attrs = {

        onOpened { drawerOpened1 = true }
        onClosed { drawerOpened1 = false }
        style {
            property("height", "fit-content")
        }

    }) {

        Content {
            Text("de.jensklingenberg.mealapp.common.Drawer")
        }
        MDCDrawerAppContent {
            MDCBody1("de.jensklingenberg.mealapp.App Content")
        }
    }
}
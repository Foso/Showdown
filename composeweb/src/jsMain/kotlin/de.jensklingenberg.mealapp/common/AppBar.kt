package de.jensklingenberg.mealapp.common

import androidx.compose.runtime.Composable
import dev.petuska.kmdc.top.app.bar.*
import dev.petuska.kmdcx.icons.MDCIcon
import dev.petuska.kmdcx.icons.mdcIcon
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun AppBar(
    navigationIcon: @Composable MDCTopAppBarSectionScope.() -> Unit = {},
    title: String? = null,
    hasInfoIcon: Boolean = false,
    onInfoIconClicked: () -> Unit = {}
) {

    Div {
        MDCTopAppBar(type = MDCTopAppBarType.Default) {
            TopAppBar(attrs = {
                style {
                    position(Position.Relative)

                }
                onNav { }
            }) {

                Row {
                    Section(align = MDCTopAppBarSectionAlign.Start) {

                        navigationIcon()
                        title?.let {
                            Title(it)
                        }
                    }

                    Section(
                        align = MDCTopAppBarSectionAlign.End,
                        attrs = {
                            attr("role", "toolbar")
                        }
                    ) {
                       if(hasInfoIcon){
                           ActionButton(attrs = {
                               mdcIcon()
                               onClick { onInfoIconClicked() }
                           }) { Text(MDCIcon.Info.type) }
                       }
                    }
                }
            }

        }
    }
}
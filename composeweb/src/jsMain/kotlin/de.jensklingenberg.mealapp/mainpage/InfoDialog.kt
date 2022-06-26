package de.jensklingenberg.mealapp.mainpage

import androidx.compose.runtime.Composable
import dev.petuska.kmdc.button.MDCButton
import dev.petuska.kmdc.button.MDCButtonType
import dev.petuska.kmdc.dialog.Content
import dev.petuska.kmdc.dialog.MDCDialog
import dev.petuska.kmdc.dialog.onClosed
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun InfoDialog(openJoinDialog: Boolean, onCloseButtonClicked: () -> Unit) {
    MDCDialog(open = openJoinDialog, attrs = {
        this.onClosed {
            onCloseButtonClicked()
        }
    }) {

        this.Content {

            Div {
                A(href = "https://github.com/Foso/KmdcExample") {
                    Text("Source code at https://github.com/Foso/KmdcExample")
                }
            }

            Div {
                A(href = "https://github.com/mpetuska/kmdc") {
                    Text("Created with Compose for Web and  https://github.com/mpetuska/kmdc")
                }
            }

            Div {
                A(href = "https://www.themealdb.com/") {
                    Text("Meal data is from https://www.themealdb.com/")
                }
            }

            Div(attrs = {
                style {
                    width(100.percent)
                    display(DisplayStyle.Flex)
                    alignItems(AlignItems.Center)
                }
            }) {
                MDCButton(text = "Close", type = MDCButtonType.Raised) {
                    onClick { onCloseButtonClicked() }

                }
            }
        }


    }
}
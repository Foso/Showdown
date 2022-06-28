package de.jensklingenberg.mealapp


import androidx.compose.runtime.*
import de.jensklingenberg.mealapp.mainpage.JKTextField
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.*


/**
 * On this view, the user can see an explanation
 * on how to use Showdown and can select a room name
 */
@Composable
fun OnboardingScreen()  {

    var roomName by remember { mutableStateOf("") }

    H1 {
        Text("Showdown - Scrum Poker Web App")
    }
    H2 {
        Text("How to:")
    }
    Div {
        H3 {
            Text("1) Choose a room name and go to the room")
        }
        JKTextField(roomName, label = "Choose new room name", onTextChange =  {
            roomName=(it)
        }, onEnterPressed = {
            window.location.href = "/#/room/${roomName}";
        })
        Button(attrs = {
            onClick {
                window.location.href = "/#/room/${roomName}";
            }
        }) {
            Text("Go to room")

        }
    }
}




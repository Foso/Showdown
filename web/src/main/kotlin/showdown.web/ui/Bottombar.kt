package challenge.ui

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.img


interface BottombarState : RState {

}

class Bottombar : RComponent<RProps, BottombarState>() {

    override fun RBuilder.render() {
        img {
            attrs {
                src = "images/logos.png"
                height = "50"
            }
        }
    }
}

fun RBuilder.bottomBar() = child(Bottombar::class) {
}

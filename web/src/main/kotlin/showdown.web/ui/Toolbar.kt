package challenge.ui

import kotlinx.html.style
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.img
import react.dom.p


interface ToolbarState : RState {

}

class BaseToolbar : RComponent<RProps, ToolbarState>() {

    override fun RBuilder.render() {

        div("toolbar") {
            attrs {
                style = kotlinext.js.js {
                    backgroundColor = "#1b66b3"
                    margin = "0"
                    height = "110px"
                }
            }
            p {
                attrs {
                    style = kotlinext.js.js {
                        margin = "0"
                    }
                }
                img {
                    attrs {
                        height = "100px"

                    }
                }
            }
        }

    }
}

fun RBuilder.toolbar() = child(BaseToolbar::class) {
}

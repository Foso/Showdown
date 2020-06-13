package de.jensklingenberg.showdown.web.ui.common


fun styleProps(textAlign: String = "", display: String = "", width: String = ""): String {
    return kotlinext.js.js {
        if (textAlign.isNotEmpty()) {
            this.textAlign = textAlign
        }

        if (width.isNotEmpty()) {
            this.width = width
        }

        if (display.isNotEmpty()) {
            this.display = display
        }
    }

}


external interface LayoutProps {
    var style: dynamic get() = definedExternally; set(value) = definedExternally

}


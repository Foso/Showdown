package showdown.web.wrapper.material.icons

import react.ComponentClass

import react.Props

@JsModule("@material-ui/core/Box")
external val BoxIconImport: dynamic

external interface BoxIconProps : Props {
    var top: Int
    var bottom: Int
    var left: Int
    var right: Int

    var position: String
    var justifyContent: String

    var display: String
    var alignItems: String
}

var Box: ComponentClass<BoxIconProps> = BoxIconImport.default

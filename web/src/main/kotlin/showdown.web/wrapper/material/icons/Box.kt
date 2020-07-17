package showdown.web.wrapper.material.icons

import react.RClass
import react.RProps

@JsModule("@material-ui/core/Box")
external val BoxIconImport: dynamic

external interface BoxIconProps :RProps {
    var top:Int
    var bottom:Int
    var left:Int
    var right:Int

    var position:String
    var justifyContent:String

    var display : String
    var alignItems:String
}

var Box: RClass<BoxIconProps> = BoxIconImport.default

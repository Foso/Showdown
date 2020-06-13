package challenge.wrapper.material


import react.RClass
import react.RProps

@JsModule("@material-ui/core/AppBar/AppBar")
external val AppBarImport: dynamic

external interface AppBarProps : RProps {
    var color: String? get() = definedExternally; set(value) = definedExternally
    var position: dynamic /* String /* "fixed" */ | String /* "absolute" */ | String /* "sticky" */ | String /* "static" */ */ get() = definedExternally; set(value) = definedExternally
}

var AppBar: RClass<AppBarProps> = AppBarImport.default


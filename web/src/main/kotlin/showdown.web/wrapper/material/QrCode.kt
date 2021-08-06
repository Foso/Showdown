package showdown.web.wrapper.material

import react.ComponentClass
import react.RClass
import react.RProps

@JsModule("react-qr-code")
external val QrCodeImport: dynamic

external interface QrCodeProps : RProps {
    var value: String? get() = definedExternally; set(value) = definedExternally

}

var QrCode: ComponentClass<QrCodeProps> = QrCodeImport.default


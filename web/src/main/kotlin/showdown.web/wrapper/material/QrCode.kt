package showdown.web.wrapper.material

import react.ComponentClass

import react.Props

@JsModule("react-qr-code")
external val QrCodeImport: dynamic

external interface QrCodeProps : Props {
    var value: String? get() = definedExternally; set(value) = definedExternally

}

var QrCode: ComponentClass<QrCodeProps> = QrCodeImport.default


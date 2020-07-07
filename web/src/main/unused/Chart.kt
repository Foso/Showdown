package showdown.web.wrapper.material

import react.RClass
import react.RProps

@JsModule("react-apexcharts")
@JsName("Chart")
external val ChartImport: dynamic

external interface ChartProps : RProps {
    var type: String? get() = definedExternally; set(value) = definedExternally
    var width: String? get() = definedExternally; set(value) = definedExternally
    var height: String? get() = definedExternally; set(value) = definedExternally
    var series: Array<Serie>? get() = definedExternally; set(value) = definedExternally
    var options:  ChartOptions? get() = definedExternally; set(value) = definedExternally

}

var ApexChart: RClass<ChartProps> = ChartImport.default

class Chart(val id: String,type:String="line")
class ChartOptions(val chart: Chart, val xaxis: Xaxis,val plotOptions: PlotOptions)
class Serie(val name: String, val data: Array<Int>)
class Xaxis(val categories: Array<String>)
class PlotOptions(val bar:Bar)
class Bar(val horizontal:Boolean=true)
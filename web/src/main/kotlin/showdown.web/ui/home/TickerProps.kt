package showdown.web.ui.home

import com.soywiz.klock.DateTime
import kotlinext.js.getOwnPropertyNames
import react.*
import kotlin.browser.*
import kotlin.js.Console
import kotlin.reflect.KClass

/*

style = kotlinext.js.js {
                        this.textAlign = "right"
                    }
 */

interface TickerProps : RProps {
    var startFrom: DateTime
}

interface TickerState : RState {
    var timerStarted: DateTime
    var nowDate : DateTime
    var diffSecs : Double
}

class Ticker(prps: TickerProps) : RComponent<TickerProps, TickerState>(prps) {

    init {
        console.log("HERHEIKRHEIR INIT")

    }
    override fun TickerState.init(props: TickerProps) {
        console.log("HERHEIKRHEIR")
        timerStarted = props.startFrom
        nowDate = DateTime.now()
        diffSecs = 0.0
    }

    var timerID: Int? = null
    var timerRunnung : Boolean = false
    override fun componentDidUpdate(prevProps: TickerProps, prevState: TickerState, snapshot: Any) {
        console.log("componentDidUpdate")
        timerRunnung=true


    }

    override fun componentDidMount() {
        with(state.timerStarted){

            if (timerID == null) {
                timerID=  window.setInterval({
                    if(timerRunnung){
                        // actually, the operation is performed on a state's copy, so it stays effectively immutable
                        setState {
                            nowDate = DateTime.now()
                            diffSecs = (nowDate - timerStarted).seconds
                           log("Hallo")

                        }
                    }

                }, 1000)
            }


        }

    }

    override fun componentWillUnmount() {

        console.log("componentWillUnmount")
        window.clearInterval(timerID!!)
    }

    override fun componentWillReceiveProps(nextProps: TickerProps) {
        console.log("componentWillReceiveProps")
        //window.clearInterval(timerID!!)
       timerRunnung=false

    }

    override fun RBuilder.render() {
        val range = state.nowDate - state.timerStarted

        +"Estimation time: ${state.diffSecs} seconds. "
    }
}

fun RBuilder.ticker(startFrom: DateTime ) = child(Ticker::class) {
    console.log2(Ticker::class,"TICKER "+startFrom.toString())
    attrs.startFrom = startFrom
}

fun Console.log2(kClass: KClass<*>,message:String) {
    console.log(kClass.toString()+": "+message)
}

fun <S : RState> Component<*, S>.log(message: String){
    console.log(message)
}
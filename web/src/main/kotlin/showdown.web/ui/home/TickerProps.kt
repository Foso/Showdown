package showdown.web.ui.home

import react.*
import react.dom.*
import kotlin.browser.*
import kotlin.js.Date
import kotlin.js.Math
import kotlin.math.abs
import kotlin.math.floor

/*

style = kotlinext.js.js {
                        this.textAlign = "right"
                    }
 */

interface TickerProps : RProps {
    var startFrom: Int
}

interface TickerState : RState {
    var secondsElapsed: Int
}

class Ticker(props: TickerProps) : RComponent<TickerProps, TickerState>(props) {
    override fun TickerState.init(props: TickerProps) {
        secondsElapsed = props.startFrom
    }

    var timerID: Int? = null

    override fun componentDidMount() {
        timerID = window.setInterval({
            // actually, the operation is performed on a state's copy, so it stays effectively immutable
            setState { secondsElapsed += 1 }
        }, 1000)
    }

    override fun componentWillUnmount() {
        window.clearInterval(timerID!!)
    }

    override fun RBuilder.render() {

        +"Estimation time: ${state.secondsElapsed} seconds. "
    }
}

fun RBuilder.ticker(startFrom: Int = 0) = child(Ticker::class) {
    attrs.startFrom = startFrom
}

package showdown.web.ui.start

import react.*
import react.dom.div
import showdown.web.ui.home.SettingsProps
import showdown.web.ui.home.TTickerState


class StartPage() : RComponent<SettingsProps, TTickerState>() {



    override fun RBuilder.render() {
       div {
           +"HALLO"
       }


    }
}

fun RBuilder.startPage() = child(StartPage::class) {

}

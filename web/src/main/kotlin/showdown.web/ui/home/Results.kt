package showdown.web.ui.home

import de.jensklingenberg.showdown.model.Result
import react.RBuilder
import react.dom.h2
import react.dom.h3

fun RBuilder.resultsList(results: List<Result>) {

    if (results.isNotEmpty()) {
        h2 {
            +"Result:"
        }

        h2 {
            val topAnswer = results.maxBy { it.optionName }?.optionName
            +"Top Voted Answer: $topAnswer"
        }

    }

    results.groupBy { it.optionName }.forEach {
        val optionName = it.key
        val optionVoters = it.value.joinToString(separator = ",") { it.voterName }

        h3 {
            +"$optionName voted by: $optionVoters"
        }
    }


}
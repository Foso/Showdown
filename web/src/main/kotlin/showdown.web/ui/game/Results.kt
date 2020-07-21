package showdown.web.ui.game

import de.jensklingenberg.showdown.model.Result
import react.RBuilder
import react.dom.h2
import react.dom.h3
import react.dom.hr
import react.dom.p

fun RBuilder.resultsList(results: List<Result>) {

    if (results.isNotEmpty()) {
        h2 {
            +"Result:"
        }

        h2 {
            val groupedResults = results.groupingBy { it.optionName }.eachCount()
            val firsttopCount = groupedResults.entries.maxBy { it.value }?.value
            val tops = groupedResults.entries.filter { it.value == firsttopCount }

            p{
                +"Top Voted Answer: ${tops.joinToString(separator = " | ") { it.key }}"
            }


        }

        results.groupBy { it.optionName }.forEach {
            val optionName = it.key
            val optionVoters = it.value.joinToString(separator = ", ") { it.voterName }

            h3 {
                +"$optionName: voted by: $optionVoters"
            }
        }
        hr {  }

    }



}
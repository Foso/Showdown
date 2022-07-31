package showdown.web.ui.game.voting

import androidx.compose.runtime.Composable
import de.jensklingenberg.showdown.model.Result
import org.jetbrains.compose.web.dom.*
import showdown.web.ui.Strings
import showdown.web.ui.Strings.Companion.RESULT

@Composable
fun ResultsList(results: List<Result>) {
    Hr { }
    H2 {
        Text(RESULT)
    }

    H2 {
        val groupedResults = results.groupingBy { it.optionName }.eachCount()
        val firsttopCount = groupedResults.entries.maxByOrNull { it.value }?.value
        val tops = groupedResults.entries.filter { it.value == firsttopCount }

        P {
            Text("${Strings.TOP_VOTED_ANSWER} ${tops.joinToString(separator = " | ") { it.key }}")
        }
    }

    results.groupBy { it.optionName }.forEach {
        val optionName = it.key
        val optionVoters = it.value.joinToString(separator = ", ") { it.voterName }

        H3 {
            Text("$optionName: voted by: $optionVoters")
        }
    }
    Hr { }

}
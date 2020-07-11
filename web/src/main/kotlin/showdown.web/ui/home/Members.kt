package showdown.web.ui.home

import de.jensklingenberg.showdown.model.Member
import kotlinx.css.TextAlign
import kotlinx.css.textAlign
import materialui.components.divider.divider
import materialui.components.list.list
import materialui.components.listitemtext.listItemText
import react.RBuilder
import react.dom.h2
import styled.css
import styled.styledH3

fun RBuilder.playersList(members: List<Member>) {
    h2 {
        +"Players (${members.size})"
    }

    list {

        members.forEach {
            listItemText {

                styledH3 {
                    css {
                        this.textAlign = TextAlign.center
                    }
                    +("Player: " + it.playerName + " Status:" + it.voteStatus + "\n")
                }

            }
            divider {}
        }
    }
}
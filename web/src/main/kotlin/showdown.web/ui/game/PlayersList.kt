package showdown.web.ui.game

import de.jensklingenberg.showdown.model.Member
import kotlinx.css.*
import materialui.components.circularprogress.circularProgress
import materialui.components.circularprogress.enums.CircularProgressVariant
import materialui.components.divider.divider
import materialui.components.list.list
import materialui.components.listitemtext.listItemText
import materialui.components.typography.enums.TypographyColor
import materialui.components.typography.enums.TypographyVariant
import materialui.components.typography.typography
import react.RBuilder
import react.dom.h2
import showdown.web.wrapper.material.icons.*
import showdown.web.wrapper.material.icons.VerifiedUserIcon
import styled.css
import styled.styledDiv
import kotlin.math.round

fun RBuilder.playersList(members: List<Member>) {


    h2 {
            GroupIcon{

            }

        +"Players (${members.size}) Voted:"


        Box{
            attrs {
                this.display="inline-flex"
                this.position="relative"
            }
            circularProgress{
                attrs {
                    variant=CircularProgressVariant.static
                    val tes =(members.filter { it.voted }.size.toDouble()/members.size.toDouble())*100

                    value= round(tes)
                }
            }
            Box{
                attrs {
                    this.display="flex"
                    this.alignItems="center"
                    this.top = 0
                    left=0
                    right=0
                    bottom=0
                    justifyContent="center"
                    this.position="absolute"
                }
                typography{
                    attrs {
                        variant= TypographyVariant.caption
                        component="div"
                        color= TypographyColor.textSecondary
                    }
                    val tes =(members.filter { it.voted }.size.toDouble()/members.size.toDouble())*100

                    +"${round(tes)}%"
                }

            }

        }



    }



    list {

            members.forEach {
                listItemText {

                    styledDiv {
                        css {
                            this.textAlign = TextAlign.center
                        }
                        +("Player: " + it.playerName + " Voted:")

                        if(it.voted){
                            styledDiv {
                                css {
                                    this.color= if(it.voted) {
                                        Color.green
                                    }else{
                                        Color.red
                                    }
                                    this.display=Display.inlineBlock
                                }
                                CheckCircleIcon{}
                            }
                        }else{
                            styledDiv {
                                css {
                                    this.color= Color.red
                                    this.display=Display.inlineBlock
                                }
                                CancelIcon{}
                            }
                        }
                        if(!it.isConnected){
                            +" Connection lost"
                        }
                    }

                }

                divider {}
            }


    }

}
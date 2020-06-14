package showdown.web.ui.new

import challenge.ui.toolbar
import challenge.usecase.MessageUseCase
import com.ccfraser.muirwik.components.MColor
import com.ccfraser.muirwik.components.button.MButtonVariant
import com.ccfraser.muirwik.components.button.mButton
import com.ccfraser.muirwik.components.mPaper
import com.ccfraser.muirwik.components.spacingUnits
import com.ccfraser.muirwik.components.table.*
import de.jensklingenberg.showdown.model.ClientVote
import de.jensklingenberg.showdown.model.Option
import de.jensklingenberg.showdown.model.Result
import kotlinx.css.*

import kotlinx.html.DIV
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.dom.*
import react.setState
import styled.css


interface MyProps : RProps

class NewView : RComponent<MyProps, HomeContract.HomeViewState>(), HomeContract.View {

    private val messageUseCase = MessageUseCase()

    private val presenter: HomeContract.Presenter by lazy {
        HomePresenter(this)
    }

    private val androidDeserts = mutableListOf(
            TestTables.Dessert(1, "Cupcake", 305, 3.7, 67, 4.3),
            TestTables.Dessert(2, "Donut", 452, 25.0, 51, 4.9),
            TestTables.Dessert(3, "Eclair", 262, 16.0, 24, 6.0)

    )

    override fun HomeContract.HomeViewState.init() {
        showSnackbar = false
        playerList = emptyList()
        hidden = true
        options = listOf(Option(0, "0"), Option(1, "1"), Option(2, "2"), Option(3, "3"), Option(5, "5"))
        results = emptyList()
    }

    override fun componentDidMount() {
        presenter.onCreate()

    }

    override fun RBuilder.render() {
        toolbar()
        messageUseCase.showErrorSnackbar(this, state.errorMessage, snackbarVisibility())

        div("imagesGrid") {

            toolbar()

            button {
                attrs {
                    text("New")
                    onClickFunction = {
                        presenter.startGame()
                    }
                }
            }



        }

        mButton(caption = "Hallo",variant = MButtonVariant.contained,color = MColor.primary) {}
        //simpleTable()
        input {

        }
    }

    private fun RDOMBuilder<DIV>.toolbar() {
        button {
            attrs {
                text("Join Game")
                onClickFunction = {
                    presenter.joinGame()
                }
            }
        }

        button {
            attrs {
                text("Reset")
                onClickFunction = {
                    presenter.reset()
                }
            }
        }
    }


    private fun snackbarVisibility(): Boolean {
        return state.showSnackbar

    }


    override fun showError(error: String) {
        setState {
            showSnackbar = true
            errorMessage = error
        }
    }


    override fun setPlayerId(list: List<ClientVote>) {
        setState {
            this.playerList = list
        }
    }

    override fun setHidden(hidden: Boolean) {
        setState {
            this.hidden = hidden
        }
    }

    override fun setOptions(list: List<Option>) {
        setState {
            this.options = list
        }
    }

    override fun setResults(message: List<Result>) {
        setState {
            this.results = message
        }
    }


    fun RBuilder.simpleTable() {
        mPaper {
            css {
                width = 100.pct
                marginTop = 3.spacingUnits
                overflowX = Overflow.auto
            }
            mTable() {
                css { minWidth = 700.px }
                mTableHead {
                    mTableRow {
                        mTableCell { +"Dessert (100g serving)" }
                        mTableCell(align = MTableCellAlign.right) { +"Calories" }
                        mTableCell(align = MTableCellAlign.right) { +"Fat (g)" }
                        mTableCell(align = MTableCellAlign.right) { +"Carbs (g)" }
                        mTableCell(align = MTableCellAlign.right) { +"Protein (g)" }
                    }
                }
                mTableBody {
                    androidDeserts.subList(0, 1).forEach {
                        mTableRow(key = it.id) {
                            mTableCell { +it.dessertName }
                            mTableCell(align = MTableCellAlign.right) { +it.calories.toString() }
                            mTableCell(align = MTableCellAlign.right) { +it.fat.toString() }
                            mTableCell(align = MTableCellAlign.right) { +it.carbs.toString() }
                            mTableCell(align = MTableCellAlign.right) { +it.protein.toString() }
                        }
                    }
                }
            }
        }
    }
}


fun RBuilder.home() = child(NewView::class) {
    this.attrs {

    }
}





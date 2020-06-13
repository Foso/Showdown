package showdown.web.ui.home

import challenge.usecase.MessageUseCase
import de.jensklingenberg.showdown.model.ClientVote
import de.jensklingenberg.showdown.model.Option
import de.jensklingenberg.showdown.model.Result

import kotlinx.html.DIV
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.dom.*
import react.setState


interface MyProps : RProps

class HomeView : RComponent<MyProps, HomeContract.HomeViewState>(), HomeContract.View {

    private val messageUseCase = MessageUseCase()

    private val presenter: HomeContract.Presenter by lazy {
        HomePresenter(this)
    }

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

        messageUseCase.showErrorSnackbar(this, state.errorMessage, snackbarVisibility())

        div("imagesGrid") {

            toolbar()

            button {
                attrs {
                    text("Show Votes")
                    onClickFunction = {
                        presenter.revealCards()
                    }
                }
            }

            div {
                state.playerList.forEach {
                    p {
                        attrs {
                            text("Player: " + it.playerName + " Number:" + it.voteValue)
                            onClickFunction = {

                            }
                        }
                    }
                }
            }

            state.options.forEach { option ->
                div {
                    button {
                        attrs {
                            text(option.text)
                            onClickFunction = {
                                presenter.onSelectedCard(option.id)
                            }
                        }
                    }
                }
            }

            p {
                attrs {
                    text("Result")
                    onClickFunction = {

                    }
                }
            }

            table("myTable") {
                tbody {
                    state.results.forEachIndexed { index, result ->
                        tr {
                            td {
                                +"${index+1} ${result.name}"
                            }
                            td {
                                +result.voters
                            }
                        }
                    }
                }
            }

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
}


fun RBuilder.home() = child(HomeView::class) {
    this.attrs {

    }
}





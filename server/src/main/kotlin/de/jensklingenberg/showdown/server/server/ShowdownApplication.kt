package de.jensklingenberg.showdown.server.server


import de.jensklingenberg.showdown.debugPort
import de.jensklingenberg.showdown.server.model.Room
import de.jensklingenberg.showdown.server.model.Session
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.application.ApplicationCallPipeline.ApplicationPhase.Plugins
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.util.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import java.time.Duration

fun main() {
    ShowdownApplication()
}

class ShowdownApplication {

    private val server = ShowdownServer()

    init {
        start()
    }

    private fun start() {
        val port = System.getenv("PORT")?.toInt() ?: debugPort
        println("SERVER STARTED on port: " + port)
        println("http://localhost:$port/")


        embeddedServer(Netty, port = port) {


            install(Compression) {
                gzip()
            }

            install(ContentNegotiation) {
                gson {
                    setPrettyPrinting()
                }
            }
            install(WebSockets)
            {
                pingPeriod = Duration.ofSeconds(30)
            }
            // This enables the use of sessions to keep information between requests/refreshes of the browser.

            install(Sessions) {
                cookie<Session>("SESSION"){
                    cookie.extensions["SameSite"] = "lax"
                }
            }

            // This adds an interceptor that will create a specific session in each request if no session is available already.
            intercept(Plugins) {
                if (call.sessions.get<Session>() == null) {
                    call.sessions.set(Session(generateNonce()))
                }
            }

            routing {
                get("") {
                    val res = this.javaClass.getResourceAsStream("/web/index.html")
                    call.respondBytes { res.readBytes() }
                }

                get("room/{roomName}/{param...}") {
                    val roomName = call.parameters["roomName"]?.substringBeforeLast("?") ?: ""
                    if (!call.request.uri.endsWith("/")) {
                        call.respondRedirect("/room/$roomName/")
                    }

                    call.respondRedirect("/#/room/$roomName")
                }

                static("web") {
                    resources("web")
                }


                webSocket("showdown") {
                    val roomName = call.parameters["room"]?.substringBeforeLast("?") ?: ""

                    val password = call.parameters["pw"] ?: ""
                    val session = call.sessions.get<Session>()

                    // We check that we actually have a session. We should always have one,
                    // since we have defined an interceptor before to set one.
                    if (session == null) {
                        close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
                        return@webSocket
                    }

                    // We notify that a member joined by calling the server handler [memberJoin]
                    // This allows to associate the session id to a specific WebSocket connection.
                    server.memberJoin(session.id, this)

                    try {
                        // We starts receiving messages (frames).
                        // Since this is a coroutine. This coroutine is suspended until receiving frames.
                        // Once the connection is closed, this consumeEach will finish and the code will continue.
                        incoming.consumeEach { frame ->
                            // Frames can be [Text], [Binary], [Ping], [Pong], [Close].
                            // We are only interested in textual messages, so we filter it.
                            if (frame is Frame.Text) {
                                // Now it is time to process the text sent from the user.
                                // At this point we have context about this connection, the session, the text and the server.
                                // So we have everything we need.

                                server.receivedMessage(session.id, frame.readText(), Room(roomName, password))
                            }
                        }
                    } finally {
                        // Either if there was an error, of it the connection was closed gracefully.
                        // We notify the server that the member left.
                        server.memberLeft(session.id, this)
                    }
                }
            }
        }.start(wait = true)
    }


}



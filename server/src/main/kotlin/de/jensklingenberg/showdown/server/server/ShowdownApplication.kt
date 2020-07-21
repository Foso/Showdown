package de.jensklingenberg.showdown.server.server

import de.jensklingenberg.showdown.server.model.Room
import de.jensklingenberg.showdown.server.model.Session
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.gzip
import io.ktor.gson.gson
import io.ktor.http.cio.websocket.*
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.request.uri
import io.ktor.response.respondBytes
import io.ktor.response.respondRedirect
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.*
import io.ktor.util.generateNonce
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
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
        val port = System.getenv("PORT")?.toInt() ?: 23567
        println("SERVER STARTED on port: " + port)
        println("http://localhost:$port/")

        embeddedServer(Netty, port) {
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
                cookie<Session>("SESSION")
            }

            // This adds an interceptor that will create a specific session in each request if no session is available already.
            intercept(ApplicationCallPipeline.Features) {
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
                    val roomName = call.parameters["roomName"] ?: ""

                    if (!call.request.uri.endsWith("/")) {
                        call.respondRedirect("/room/$roomName/")
                    }

                    call.respondRedirect("/#/room/$roomName")
                }

                static("web") {
                    resources("web")
                }


                webSocket("showdown") {
                    val roomName = call.parameters["room"] ?: ""
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



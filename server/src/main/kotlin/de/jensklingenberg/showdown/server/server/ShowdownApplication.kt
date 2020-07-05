package de.jensklingenberg.showdown.server.server

import de.jensklingenberg.showdown.server.model.ChatSession
import de.jensklingenberg.showdown.server.game.ShowdownServer
import de.jensklingenberg.showdown.server.model.Room
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.*
import io.ktor.util.generateNonce
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import kotlinx.coroutines.channels.consumeEach
import java.io.File
import java.io.InputStream
import java.io.OutputStream

fun main() {
    ShowdownApplication()
}
fun InputStream.toFile(path: String): File {
  val file = File(path)

      file.outputStream().use { this.copyTo(it) }

    return file
}

class ShowdownApplication {

    private val server = ShowdownServer()

    init {
        start()
    }


    private fun start() {
        val port = System.getenv("PORT")?.toInt() ?: 23567
        println("SERVER STARTED on port: " + port)
        println("http://localhost:$port/room/jens/")


        embeddedServer(Netty, port) {
            install(ContentNegotiation) {
                gson {
                    setPrettyPrinting()
                }

            }
            install(WebSockets)
            // This enables the use of sessions to keep information between requests/refreshes of the browser.

            install(Sessions) {
                cookie<ChatSession>("SESSION")
            }

            // This adds an interceptor that will create a specific session in each request if no session is available already.
            intercept(ApplicationCallPipeline.Features) {
                if (call.sessions.get<ChatSession>() == null) {
                    call.sessions.set(ChatSession(generateNonce()))
                }
            }

            routing {
                get("") {
                    val roomName = call.parameters["room"] ?: ""
                    //call.respond("I'm alive! Roomname:"+roomName)
                    call.respond(HttpStatusCode.Accepted, "Its working ")
                }

                get("hello") {
                    call.respond(HttpStatusCode.Accepted, "Hello ")
                }
                get("room/{roomName}/{param...}") {
                    println("MY:room/{roomName}/{param...}")
                    val roomName = call.parameters["roomName"] ?: ""
                    val roomNamepar = call.parameters["param"] ?: "index.html"
                    val res=   this.javaClass.getResourceAsStream("/web/$roomNamepar")
                    println("FILEPATH"+res)

                   call.respondBytes { res.readBytes() }
                }

                static("game") {

                 //   staticRootFolder = File("/web")
                    resources("web")

                }


                webSocket("showdown") {
                    val roomName = call.parameters["room"] ?: ""
                    val password = call.parameters["pw"] ?: ""
                    val session = call.sessions.get<ChatSession>()

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

                                server.receivedMessage(session.id, frame.readText(), Room(roomName,password))
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

//

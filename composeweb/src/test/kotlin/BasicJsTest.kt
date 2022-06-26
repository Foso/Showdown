
import showdown.web.game.GameRepository
import showdown.web.network.GameApiClient
import kotlin.test.Test
import kotlin.test.assertEquals

class BasicJsTest {
    @Test
    fun testDecode() {
        val client = GameApiClient()
        val gameRepository = GameRepository(client)
        gameRepository.joinRoom("abc","")
        assertEquals("abc",gameRepository.getPlayerName())
    }
}

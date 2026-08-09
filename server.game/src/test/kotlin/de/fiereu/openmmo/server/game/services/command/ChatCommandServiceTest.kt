package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.common.CharacterPermissions
import de.fiereu.openmmo.common.enums.CharacterGender
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.net.game.packets.ChatMessagePacket
import de.fiereu.openmmo.server.game.storage.CharacterStore
import de.fiereu.openmmo.server.game.storage.EntityIdService
import de.fiereu.openmmo.server.game.testsupport.FakeCharacterRepository
import de.fiereu.openmmo.server.game.testsupport.FakeSession
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

private class RecordingCommand(
    override val permission: Int = 0,
    private val fail: Boolean = false,
) : ChatCommand {
  override val name = "secret"
  override val usage = "/secret"
  override val description = "only for developers"
  var ran = false

  override suspend fun run(ctx: CommandContext) {
    ran = true
    if (fail) error("boom")
  }
}

@OptIn(ExperimentalCoroutinesApi::class)
class ChatCommandServiceTest :
    FunSpec({
      fun FakeSession.replies() = sent.filterIsInstance<ChatMessagePacket>().map { it.message }

      test("ordinary chat is left alone") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id
          val session = FakeSession(characterId = charId)
          val service = ChatCommandService(store, setOf(HelpCommand(), PosCommand()))

          service.tryHandle(session, "hello there") shouldBe false
          session.sent shouldBe emptyList()
        }
      }

      test("pos reports the tile the player stands on") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id
          val session = FakeSession(characterId = charId, regionId = 0, bankId = 4, mapId = 3)
          val service = ChatCommandService(store, setOf(HelpCommand(), PosCommand()))

          service.tryHandle(session, "/pos") shouldBe true

          session.replies().single() shouldContain "region 0 bank 4 map 3"
        }
      }

      test("a command name is matched without case and around stray spaces") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id
          val session = FakeSession(characterId = charId)
          val service = ChatCommandService(store, setOf(HelpCommand(), PosCommand()))

          service.tryHandle(session, "  /PoS  ") shouldBe true

          session.replies().single() shouldContain "region"
        }
      }

      test("help lists only the commands the caller may run") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id
          val session = FakeSession(characterId = charId)
          val gated = RecordingCommand(CharacterPermissions.DEVELOPER)
          val service = ChatCommandService(store, setOf(HelpCommand(), PosCommand(), gated))

          service.tryHandle(session, "/help") shouldBe true

          session.replies().any { it.startsWith("/secret") } shouldBe false
          session.replies().any { it.startsWith("/pos") } shouldBe true
        }
      }

      test("an unknown command is answered without echoing the rest of the line") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id
          val session = FakeSession(characterId = charId)
          val service = ChatCommandService(store, setOf(HelpCommand(), PosCommand()))

          service.tryHandle(session, "/nonsense hunter2") shouldBe true

          val reply = session.replies().single()
          reply shouldContain "Unknown command: nonsense"
          reply.contains("hunter2") shouldBe false
        }
      }

      test("a gated command needs its permission bit") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id
          val session = FakeSession(characterId = charId)
          val gated = RecordingCommand(CharacterPermissions.DEVELOPER)
          val service = ChatCommandService(store, setOf(HelpCommand(), PosCommand(), gated))

          service.tryHandle(session, "/secret") shouldBe true
          gated.ran shouldBe false
          session.replies().single() shouldContain "Unknown command"

          val info = store.getCharacter(charId)!!.info
          store.updateCharacter(
              info.copy(permissions = info.permissions or CharacterPermissions.DEVELOPER))
          session.sent.clear()

          service.tryHandle(session, "/secret") shouldBe true
          gated.ran shouldBe true
          session.sent shouldBe emptyList()
        }
      }

      test("a command that throws answers instead of killing the connection") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id
          val session = FakeSession(characterId = charId)
          val service =
              ChatCommandService(store, setOf(RecordingCommand(permission = 0, fail = true)))

          service.tryHandle(session, "/secret") shouldBe true

          session.replies().single() shouldContain "/secret failed."
        }
      }

      test("a bare slash points at help") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val charId = store.createCharacter(1, "Red", CharacterGender.MALE, Region.KANTO).info.id
          val session = FakeSession(characterId = charId)
          val service = ChatCommandService(store, setOf(HelpCommand(), PosCommand()))

          service.tryHandle(session, "/   ") shouldBe true
          session.replies().single() shouldContain "/help"
        }
      }

      test("a command from a session with no character is refused") {
        runTest {
          val store = CharacterStore(FakeCharacterRepository(), EntityIdService(), backgroundScope)
          val session = FakeSession()
          val service = ChatCommandService(store, setOf(HelpCommand(), PosCommand()))

          service.tryHandle(session, "/pos") shouldBe true
          session.replies().single() shouldContain "not in the world"
        }
      }
    })

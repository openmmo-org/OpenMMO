package de.fiereu.openmmo.server.game.script

/** An overworld script the player can trigger. It runs as a coroutine so it can wait on dialog. */
fun interface Script {
  suspend fun run(ctx: ScriptContext)
}

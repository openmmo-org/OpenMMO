package de.fiereu.openmmo.net.game

import de.fiereu.bytecodec.ByteArrayReadBuffer

private object Fixtures

/**
 * Reads a captured payload from `src/test/resources/fixtures`. The path is
 * `<server>/<direction>/<packet id>/<name>.bin` and the name ends in the client version the capture
 * came from.
 */
fun fixture(path: String): ByteArrayReadBuffer =
    ByteArrayReadBuffer(
        Fixtures.javaClass.getResourceAsStream("/fixtures/$path")?.readBytes()
            ?: error("no fixture at /fixtures/$path"))

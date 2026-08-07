package de.fiereu.openmmo.common.test

import de.fiereu.bytecodec.ByteArrayReadBuffer

private object Fixtures

/**
 * Reads a captured payload from a module's `src/test/resources/fixtures`. The path is
 * `<server>/<direction>/<packet id>/<name>.bin` and the name ends in the client version the capture
 * came from, when that is known.
 */
fun fixture(path: String): ByteArray =
    Fixtures.javaClass.getResourceAsStream("/fixtures/$path")?.readBytes()
        ?: error("no fixture at /fixtures/$path")

fun fixtureBuffer(path: String): ByteArrayReadBuffer = ByteArrayReadBuffer(fixture(path))

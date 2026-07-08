package de.fiereu.openmmo.server.game.battle

import de.fiereu.openmmo.server.game.domain.OwnedPokemon
import de.fiereu.openmmo.server.game.domain.StatBlock

/**
 * Adapter: the server's domain [OwnedPokemon] → the sidecar's [WirePokemon] JSON boundary shape.
 * One-directional — BattleService works in domain types and converts only at the sidecar edge, so a
 * caught mon is persisted as the ORIGINAL domain object (lossless), never round-tripped through
 * wire.
 *
 * Enum names map 1:1 to the sidecar's lowercase strings (Nature: HARDY→"hardy"; Gender:
 * MALE→"male"). Stats already share the spa/spd/spe convention (see WireStats docs / packet-map).
 */
fun OwnedPokemon.toWire(): WirePokemon =
    WirePokemon(
        uid = uid,
        speciesId = speciesId,
        level = level,
        exp = exp,
        nature = nature.name.lowercase(),
        ability = ability,
        gender = gender.name.lowercase(),
        shiny = shiny,
        ivs = ivs.toWireStats(),
        evs = evs.toWireStats(),
        moves =
            moves.map { WireMove(moveId = it.moveId, ppUp = it.ppUp, ppCurrent = it.ppCurrent) },
        heldItem = heldItem,
        friendship = friendship,
        otWallet = otWallet,
        otName = otName,
        pid = pid.toInt(),
        metLevel = metLevel,
        metLocation = metLocation,
    )

private fun StatBlock.toWireStats(): WireStats = WireStats(hp, atk, def, spa, spd, spe)

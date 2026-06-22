package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class PlayerVariableEntry(
    val key: Byte,
    val value: Short,
)

data class LocalPlayerStatePacket(
    val region: Byte,
    val mapId: Short,
    val moveSpeed: Float,
    val x: Short,
    val y: Short,
    val z: Short,
    val money: Int,
    val gender: Byte,
    val skinTone: Short,
    val hairColor: Short,
    val playtime: Double,
    val flags: Byte,
    val partyDex: List<Short>,
    val partyForms: List<Byte>,
    val pokedexSeen: List<Short>,
    val pokedexCaught: List<Short>,
    val badges: List<Short>,
    val variables: List<PlayerVariableEntry>,
)

private val PlayerVariableEntryCodec: Codec<PlayerVariableEntry> =
    object : PacketCodec<PlayerVariableEntry>() {
        override fun CodecScope<PlayerVariableEntry>.body(): PlayerVariableEntry {
            val key = field(S8) { it.key }
            val value = field(S16LE) { it.value }
            return PlayerVariableEntry(key, value)
        }
    }

object LocalPlayerStatePacketCodec : PacketCodec<LocalPlayerStatePacket>() {
    override fun CodecScope<LocalPlayerStatePacket>.body(): LocalPlayerStatePacket {
        val region = field(S8) { it.region }
        val mapId = field(S16LE) { it.mapId }
        val moveSpeed = field(F32LE) { it.moveSpeed }
        val x = field(S16LE) { it.x }
        val y = field(S16LE) { it.y }
        val z = field(S16LE) { it.z }
        val money = field(S32LE) { it.money }
        val gender = field(S8) { it.gender }
        val skinTone = field(S16LE) { it.skinTone }
        val hairColor = field(S16LE) { it.hairColor }
        val playtime = field(F64LE) { it.playtime }
        val flags = field(S8) { it.flags }
        val partyDex = field(S16LE.listPrefixed(U16LE)) { it.partyDex }
        val partyForms = field(S8.listPrefixed(U8)) { it.partyForms }
        val pokedexSeen = field(S16LE.listPrefixed(U16LE)) { it.pokedexSeen }
        val pokedexCaught = field(S16LE.listPrefixed(U16LE)) { it.pokedexCaught }
        val badges = field(S16LE.listPrefixed(U8)) { it.badges }
        val variables = field(PlayerVariableEntryCodec.listPrefixed(U16LE)) { it.variables }
        return LocalPlayerStatePacket(
            region,
            mapId,
            moveSpeed,
            x,
            y,
            z,
            money,
            gender,
            skinTone,
            hairColor,
            playtime,
            flags,
            partyDex,
            partyForms,
            pokedexSeen,
            pokedexCaught,
            badges,
            variables,
        )
    }
}

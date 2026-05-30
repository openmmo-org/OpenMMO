package de.fiereu.openmmo.net.game.codecs

import de.fiereu.bytecodec.Codec
import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.U16LE
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.Utf16LeNullTerminated
import de.fiereu.bytecodec.bytesPrefixed
import de.fiereu.bytecodec.imap
import de.fiereu.openmmo.common.CharacterInfo
import java.time.LocalDateTime
import java.time.ZoneOffset

internal val EpochSecondsS32LE: Codec<LocalDateTime> =
    S32LE.imap(
        decode = { LocalDateTime.ofEpochSecond(it.toLong(), 0, ZoneOffset.UTC) },
        encode = { it.toEpochSecond(ZoneOffset.UTC).toInt() },
    )

private val TrailingBytes = bytesPrefixed(U16LE)

class CharacterInfoCodec(private val withExtraLong: Boolean) : PacketCodec<CharacterInfo>() {
  override fun CodecScope<CharacterInfo>.body(): CharacterInfo {
    val id = field(S64LE, CharacterInfo::id)
    val name = field(Utf16LeNullTerminated, CharacterInfo::name)
    val namePrefix = field(Utf16LeNullTerminated) { "" }
    val userId = field(S32LE, CharacterInfo::userId)
    val rivalSex = field(S8, CharacterInfo::rivalSex)
    val lastLogin = field(EpochSecondsS32LE, CharacterInfo::lastLogin)
    if (withExtraLong) field(S64LE) { 0L }
    val createdAt = field(EpochSecondsS32LE, CharacterInfo::createdAt)
    field(S32LE) { 0 }
    field(S8) { 0 }
    field(S32LE) { 0 }
    val money = field(S32LE, CharacterInfo::money)
    field(S16LE) { 0 }
    field(S32LE) { 0 }
    val permissions = field(U8) { it.permissions and 0xFF }
    field(S8) { 0 }
    field(S8) { 0 }
    field(S32LE) { 0 }
    repeat(8) { field(S8) { 0 } }
    val remainingSafariSteps = field(S16LE, CharacterInfo::remainingSafariSteps)
    val remainingSafariBalls = field(S8, CharacterInfo::remainingSafariBalls)
    field(S32LE) { 0 }
    val pcExtraSlots = field(S8, CharacterInfo::pcExtraSlots)
    val battleBoxExtraSlots = field(S8, CharacterInfo::battleBoxExtraSlots)
    val templateAmount = field(S8, CharacterInfo::templateAmount)
    field(S8) { 0 }
    field(S8) { 0 }
    field(S8) { 0 }
    val positionRegionId = field(S8, CharacterInfo::positionRegionId)
    val positionBankId = field(S8, CharacterInfo::positionBankId)
    val positionMapId = field(S8, CharacterInfo::positionMapId)
    field(S8) { 0 }
    val positionX = field(S16LE, CharacterInfo::positionX)
    val positionY = field(S16LE, CharacterInfo::positionY)
    field(S8) { 0 }
    field(S8) { 0 }
    val repelLeft = field(S16LE, CharacterInfo::repelLeft)
    val repelItemId = field(S16LE, CharacterInfo::repelItemId)
    field(S8) { 0 }
    val lureItemId = field(S16LE, CharacterInfo::lureItemId)
    val lureLeft = field(S16LE, CharacterInfo::lureLeft)
    field(TrailingBytes) { ByteArray(0) }
    return CharacterInfo(
        id = id,
        name = name,
        namePrefix = namePrefix,
        userId = userId,
        rivalSex = rivalSex,
        lastLogin = lastLogin,
        createdAt = createdAt,
        money = money,
        permissions = permissions,
        remainingSafariSteps = remainingSafariSteps,
        remainingSafariBalls = remainingSafariBalls,
        pcExtraSlots = pcExtraSlots,
        battleBoxExtraSlots = battleBoxExtraSlots,
        templateAmount = templateAmount,
        positionRegionId = positionRegionId,
        positionBankId = positionBankId,
        positionMapId = positionMapId,
        positionX = positionX,
        positionY = positionY,
        repelLeft = repelLeft,
        repelItemId = repelItemId,
        lureLeft = lureLeft,
        lureItemId = lureItemId,
    )
  }
}

val CharacterInfoCodecShort: Codec<CharacterInfo> = CharacterInfoCodec(withExtraLong = false)
val CharacterInfoCodecLong: Codec<CharacterInfo> = CharacterInfoCodec(withExtraLong = true)

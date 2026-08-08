package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.*

/** The body of a nested battle action event, selected by its [BattleEventType]. */
sealed interface BattleEventBody {
  /** Event 0, BattleHpUpdate: the entity's resulting hp. */
  data class HpUpdate(val currentHp: Short) : BattleEventBody

  /**
   * Event 1, BattleStatChange. [stat] is the wire stat id (1 atk, 2 def, 3 spd, ...) and
   * [stageDelta] the signed number of stages (-1 down one, +2 up two). [changeType] is 0 for a
   * normal stage change.
   */
  data class StatChange(val stat: Byte, val stageDelta: Short, val changeType: Byte = 0) :
      BattleEventBody

  /** Event 5, BattlePokemonFainted. [playFaintAnimation] plays the faint-in-place animation. */
  data class Faint(val playFaintAnimation: Boolean) : BattleEventBody

  /** Event 4, meaning unknown. It carries no body. Effectiveness rides in the outcome word. */
  data object EffectivenessMessage : BattleEventBody

  /** Event 0x40, BattleMoveFailed. [moveId] names the move in the "it failed" message. */
  data class MoveFailed(val moveId: Short) : BattleEventBody
}

private val HpUpdateBodyCodec: Codec<BattleEventBody> =
    object : PacketCodec<BattleEventBody>() {
      override fun CodecScope<BattleEventBody>.body(): BattleEventBody {
        val currentHp = field(S16LE) { (it as BattleEventBody.HpUpdate).currentHp }
        return BattleEventBody.HpUpdate(currentHp)
      }
    }

private const val STAT_INDEX_MASK = 0x7F

private val StatChangeBodyCodec: Codec<BattleEventBody> =
    object : PacketCodec<BattleEventBody>() {
      override fun CodecScope<BattleEventBody>.body(): BattleEventBody {
        val changeType = field(S8) { (it as BattleEventBody.StatChange).changeType }
        // Only the low seven bits name the stat. The top bit is a separate emphasis flag, and the
        // direction rides in the signed stage count rather than here.
        val stat = field(S8) { (it as BattleEventBody.StatChange).stat }
        val stages = field(S8) { (it as BattleEventBody.StatChange).stageDelta.toByte() }
        // 0xFF in every capture.
        reserved(0xFF)
        return BattleEventBody.StatChange(
            (stat.toInt() and STAT_INDEX_MASK).toByte(), stages.toShort(), changeType)
      }
    }

private val FaintBodyCodec: Codec<BattleEventBody> =
    object : PacketCodec<BattleEventBody>() {
      override fun CodecScope<BattleEventBody>.body(): BattleEventBody {
        val playFaintAnimation = field(Bool) { (it as BattleEventBody.Faint).playFaintAnimation }
        return BattleEventBody.Faint(playFaintAnimation)
      }
    }

private val EffectivenessMessageBodyCodec: Codec<BattleEventBody> =
    object : PacketCodec<BattleEventBody>() {
      override fun CodecScope<BattleEventBody>.body(): BattleEventBody =
          BattleEventBody.EffectivenessMessage
    }

private val MoveFailedBodyCodec: Codec<BattleEventBody> =
    object : PacketCodec<BattleEventBody>() {
      override fun CodecScope<BattleEventBody>.body(): BattleEventBody {
        val moveId = field(S16LE) { (it as BattleEventBody.MoveFailed).moveId }
        return BattleEventBody.MoveFailed(moveId)
      }
    }

/**
 * A nested battle action event's type, the client's event id. Each type holds the codec for its
 * body. The enum grows as we capture more of the client's event types.
 */
enum class BattleEventType(val id: Int, val codec: Codec<BattleEventBody>) {
  HP_UPDATE(id = 0, codec = HpUpdateBodyCodec),
  STAT_CHANGE(id = 1, codec = StatChangeBodyCodec),
  EFFECTIVENESS_MESSAGE(id = 4, codec = EffectivenessMessageBodyCodec),
  POKEMON_FAINTED(id = 5, codec = FaintBodyCodec),
  MOVE_FAILED(id = 0x40, codec = MoveFailedBodyCodec);

  companion object {
    fun ofId(id: Int): BattleEventType = entries.first { it.id == id }

    fun ofBody(body: BattleEventBody): BattleEventType =
        when (body) {
          is BattleEventBody.HpUpdate -> HP_UPDATE
          is BattleEventBody.StatChange -> STAT_CHANGE
          is BattleEventBody.EffectivenessMessage -> EFFECTIVENESS_MESSAGE
          is BattleEventBody.Faint -> POKEMON_FAINTED
          is BattleEventBody.MoveFailed -> MOVE_FAILED
        }
  }
}

private const val FLAG_ENTITY_A = 0x01
private const val FLAG_ENTITY_B = 0x02

/**
 * One nested battle action event. The variable header is a type id, a presence-flags byte, then the
 * entity ids the flags select. [entityA] and [entityB] are present only when their flag bit is set.
 */
data class BattleActionEvent(val entityA: Long?, val entityB: Long?, val body: BattleEventBody)

private val BattleActionEventCodec: Codec<BattleActionEvent> =
    object : PacketCodec<BattleActionEvent>() {
      override fun CodecScope<BattleActionEvent>.body(): BattleActionEvent {
        val typeId = field(U8) { BattleEventType.ofBody(it.body).id }
        val aux =
            field(U8) {
              var flags = 0
              if (it.entityA != null) flags = flags or FLAG_ENTITY_A
              if (it.entityB != null) flags = flags or FLAG_ENTITY_B
              flags
            }
        val entityA = optionalField((aux and FLAG_ENTITY_A) != 0, S64LE) { it.entityA }
        val entityB = optionalField((aux and FLAG_ENTITY_B) != 0, S64LE) { it.entityB }
        val body = field(BattleEventType.ofId(typeId).codec) { it.body }
        return BattleActionEvent(entityA, entityB, body)
      }
    }

/** One mon a move affected: its entity, its move short, and the events applied to it. */
data class BattleEffectTarget(
    val entityId: Long,
    val targetMove: Short,
    val subEvents: List<BattleActionEvent>,
)

object BattleEffectTargetCodec : PacketCodec<BattleEffectTarget>() {
  override fun CodecScope<BattleEffectTarget>.body(): BattleEffectTarget {
    val entityId = field(S64LE) { it.entityId }
    val targetMove = field(S16LE) { it.targetMove }
    val subEvents = field(BattleActionEventCodec.listPrefixed(U8)) { it.subEvents }
    return BattleEffectTarget(entityId, targetMove, subEvents)
  }
}

data class BattleEntityMoveEventPacket(
    val sourceEntity: Long,
    val sourceMove: Short,
    val kind: Byte,
    val targets: List<BattleEffectTarget>,
)

object BattleEntityMoveEventPacketCodec : PacketCodec<BattleEntityMoveEventPacket>() {
  override fun CodecScope<BattleEntityMoveEventPacket>.body(): BattleEntityMoveEventPacket {
    val sourceEntity = field(S64LE) { it.sourceEntity }
    val sourceMove = field(S16LE) { it.sourceMove }
    val kind = field(S8) { it.kind }
    val targets = field(BattleEffectTargetCodec.listPrefixed(U8)) { it.targets }
    return BattleEntityMoveEventPacket(sourceEntity, sourceMove, kind, targets)
  }
}

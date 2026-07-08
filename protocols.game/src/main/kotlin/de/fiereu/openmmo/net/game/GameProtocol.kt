package de.fiereu.openmmo.net.game

import de.fiereu.network.Protocol
import de.fiereu.network.bidi
import de.fiereu.network.c2s
import de.fiereu.network.s2c
import de.fiereu.openmmo.net.game.packets.*

object GameProtocol : Protocol() {
  override val compressed: Boolean = true

  init {
    c2s<JoinPacket>(0x01u, JoinPacketCodec)
    s2c<JoinResponsePacket>(0x01u, JoinResponsePacketCodec)

    c2s<RequestCharactersPacket>(0x02u, RequestCharactersPacketCodec)
    s2c<CharactersListPacket>(0x02u, CharactersListPacketCodec)

    c2s<CreateCharacterPacket>(0x03u, CreateCharacterPacketCodec)
    s2c<FriendRosterDeltaPacket>(0x03u, FriendRosterDeltaPacketCodec)

    c2s<SelectCharacterPacket>(0x04u, SelectCharacterPacketCodec)
    s2c<SelectedCharacterPacket>(0x04u, SelectedCharacterPacketCodec)

    c2s<RequestPlayerPacket>(0x05u, RequestPlayerPacketCodec)
    s2c<LoadEntityPacket>(0x05u, LoadEntityPacketCodec)

    c2s<MovementPacket>(0x06u, MovementPacketCodec)

    c2s<FaceDirectionPacket>(0x07u, FaceDirectionPacketCodec)
    s2c<EntityFaceTurnPacket>(0x07u, EntityFaceTurnPacketCodec)

    c2s<ChatMessageSendPacket>(0x08u, ChatMessageSendPacketCodec)
    s2c<EntityLeavePacket>(0x08u, EntityLeavePacketCodec)

    bidi<ChatMessagePacket>(0x09u, ChatMessagePacketCodec)

    c2s<BattleMoveUsePacket>(0x0Au, BattleMoveUsePacketCodec)
    s2c<WorldFlagTableResetPacket>(0x0Au, WorldFlagTableResetPacketCodec)

    c2s<EvolutionPromptResponsePacket>(0x0Bu, EvolutionPromptResponsePacketCodec)
    s2c<WorldFlagSetPacket>(0x0Bu, WorldFlagSetPacketCodec)

    c2s<BattlePartySwitchPacket>(0x0Cu, BattlePartySwitchPacketCodec)
    s2c<LocalCharacterDeltaPacket>(0x0Cu, LocalCharacterDeltaPacketCodec)

    bidi<DialogDataPacket>(0x0Du, DialogDataPacketCodec)

    bidi<DialogStatePacket>(0x0Eu, DialogStatePacketCodec)

    c2s<PokemonListAddPacket>(0x0Fu, PokemonListAddPacketCodec)
    s2c<EntityPresencePacket>(0x0Fu, EntityPresencePacketCodec)

    bidi<LoadMapPacket>(0x10u, LoadMapPacketCodec)

    c2s<PartyMemberSelectPacket>(0x11u, PartyMemberSelectPacketCodec)
    s2c<NpcUpdatePacket>(0x11u, NpcUpdatePacketCodec)

    bidi<NpcSpawnPacket>(0x12u, NpcSpawnPacketCodec)

    bidi<PokemonContainerPacket>(0x13u, PokemonContainerPacketCodec)

    c2s<EntityActionRequestPacket>(0x14u, EntityActionRequestPacketCodec)
    s2c<SinglePokemonAddPacket>(0x14u, SinglePokemonAddPacketCodec)

    c2s<ChatLocalePreferencesPacket>(0x15u, ChatLocalePreferencesPacketCodec)
    s2c<SocialListEntryRemovePacket>(0x15u, SocialListEntryRemovePacketCodec)

    c2s<InGameChallengeResponsePacket>(0x16u, InGameChallengeResponsePacketCodec)
    s2c<BattleEntityDeltaPacket>(0x16u, BattleEntityDeltaPacketCodec)

    c2s<StorageBoxClosePacket>(0x17u, StorageBoxClosePacketCodec)
    s2c<SocialEntryFlagSetPacket>(0x17u, SocialEntryFlagSetPacketCodec)

    c2s<MonsterStatusConditionPacket>(0x18u, MonsterStatusConditionPacketCodec)
    s2c<SocialEntryPresencePacket>(0x18u, SocialEntryPresencePacketCodec)

    c2s<MonsterFormActionPacket>(0x19u, MonsterFormActionPacketCodec)
    s2c<SocialEntryRenamePacket>(0x19u, SocialEntryRenamePacketCodec)

    s2c<EntityMovePpPacket>(0x1Au, EntityMovePpPacketCodec)

    bidi<MapTransitionPacket>(0x1Bu, MapTransitionPacketCodec)

    c2s<StorageBoxFilterPacket>(0x1Cu, StorageBoxFilterPacketCodec)
    s2c<PokedexSpeciesResetPacket>(0x1Cu, PokedexSpeciesResetPacketCodec)

    c2s<PokemonFlagsUpdatePacket>(0x1Du, PokemonFlagsUpdatePacketCodec)
    s2c<PokedexSpeciesUnlockPacket>(0x1Du, PokedexSpeciesUnlockPacketCodec)

    s2c<OverworldWeatherControlPacket>(0x1Eu, OverworldWeatherControlPacketCodec)

    s2c<OverworldParticleSpawnPacket>(0x1Fu, OverworldParticleSpawnPacketCodec)

    c2s<NullPacket>(0x20u, NullPacketCodec)
    s2c<TokenPayloadPacket>(0x20u, TokenPayloadPacketCodec)

    c2s<WarpTileInteractPacket>(0x21u, WarpTileInteractPacketCodec)
    s2c<DialogActionPacket>(0x21u, DialogActionPacketCodec)

    bidi<EntityInteractPacket>(0x22u, EntityInteractPacketCodec)

    c2s<ExchangeItemRequestPacket>(0x23u, ExchangeItemRequestPacketCodec)
    s2c<EventOverlayStatePacket>(0x23u, EventOverlayStatePacketCodec)

    c2s<ShopSellRequestPacket>(0x24u, ShopSellRequestPacketCodec)
    s2c<NpcPanelTogglePacket>(0x24u, NpcPanelTogglePacketCodec)

    bidi<DialogChoicePacket>(0x25u, DialogChoicePacketCodec)

    bidi<DialogOptionPacket>(0x26u, DialogOptionPacketCodec)

    c2s<PlayerStopMovingPacket>(0x27u, PlayerStopMovingPacketCodec)
    s2c<BattlePromptSwitchPacket>(0x27u, BattlePromptSwitchPacketCodec)

    c2s<TypedBinaryDataPacket>(0x28u, TypedBinaryDataPacketCodec)
    s2c<EntityInteractionFlagPacket>(0x28u, EntityInteractionFlagPacketCodec)

    c2s<CustomizeCharacterAppearancePacket>(0x29u, CustomizeCharacterAppearancePacketCodec)
    s2c<ShopPriceTablePacket>(0x29u, ShopPriceTablePacketCodec)

    c2s<TeamNameChangePacket>(0x2Au, TeamNameChangePacketCodec)
    s2c<BattleStatusFlagPacket>(0x2Au, BattleStatusFlagPacketCodec)

    c2s<SetCharacterNamePacket>(0x2Bu, SetCharacterNamePacketCodec)
    s2c<FollowerAdvancePacket>(0x2Bu, FollowerAdvancePacketCodec)

    c2s<NpcDialogResponsePacket>(0x2Cu, NpcDialogResponsePacketCodec)
    s2c<EntityTitleTagPacket>(0x2Cu, EntityTitleTagPacketCodec)

    c2s<EntityInteractRequestPacket>(0x2Du, EntityInteractRequestPacketCodec)
    s2c<MapCellTilesetPacket>(0x2Du, MapCellTilesetPacketCodec)

    c2s<SendDirectMessagePacket>(0x2Eu, SendDirectMessagePacketCodec)
    s2c<EntityRenamePacket>(0x2Eu, EntityRenamePacketCodec)

    c2s<DialogResponsePacket>(0x2Fu, DialogResponsePacketCodec)
    s2c<WorldJoinConfirmPacket>(0x2Fu, WorldJoinConfirmPacketCodec)

    c2s<BattleActionPacket>(0x30u, BattleActionPacketCodec)
    s2c<BattleOpenPacket>(0x30u, BattleOpenPacketCodec) // wild battle-open (validated vs capture)

    s2c<BattleBulkStatePacket>(0x31u, BattleBulkStatePacketCodec)

    c2s<BattleActionSelectPacket>(0x32u, BattleActionSelectPacketCodec)
    s2c<BattleQueuedEventPacket>(0x32u, BattleQueuedEventPacketCodec)

    c2s<MapLoadedAckPacket>(0x33u, MapLoadedAckPacketCodec)
    s2c<BattleEntityMoveEventPacket>(0x33u, BattleEntityMoveEventPacketCodec)

    c2s<SpectateRequestPacket>(0x34u, SpectateRequestPacketCodec)
    s2c<BattleSlotEventEnumPacket>(0x34u, BattleSlotEventEnumPacketCodec)

    c2s<LeaveBattlePacket>(0x35u, LeaveBattlePacketCodec)
    s2c<BattleSlotMoveEventPacket>(0x35u, BattleSlotMoveEventPacketCodec)

    c2s<RequestNextBattleSequencePacket>(0x36u, RequestNextBattleSequencePacketCodec)
    s2c<BattleSlotFlagEventPacket>(0x36u, BattleSlotFlagEventPacketCodec)

    c2s<BattleSlotActionPacket>(0x37u, BattleSlotActionPacketCodec)
    s2c<BattleListEventPacket>(0x37u, BattleListEventPacketCodec)

    c2s<BattleSwitchSelectionsPacket>(0x38u, BattleSwitchSelectionsPacketCodec)
    s2c<BattleEntityActionEventPacket>(0x38u, BattleEntityActionEventPacketCodec)

    c2s<BattleUseItemPacket>(0x39u, BattleUseItemPacketCodec)
    s2c<BattleLabeledMoveEventPacket>(0x39u, BattleLabeledMoveEventPacketCodec)

    c2s<SetBattleMonsterAppearancePacket>(0x3Au, SetBattleMonsterAppearancePacketCodec)
    s2c<BattleEnumPairEventPacket>(0x3Au, BattleEnumPairEventPacketCodec)

    c2s<PrismaticPearlTransferPacket>(0x3Bu, PrismaticPearlTransferPacketCodec)
    s2c<BattleEmptyEventPacket>(0x3Bu, BattleEmptyEventPacketCodec)

    c2s<SetActiveMovesetPacket>(0x3Cu, SetActiveMovesetPacketCodec)
    s2c<ServerChatTemplate5002Packet>(0x3Cu, ServerChatTemplate5002PacketCodec)

    c2s<BattleCancelRequestPacket>(0x3Du, BattleCancelRequestPacketCodec)
    s2c<ServerChatTemplate5003Packet>(0x3Du, ServerChatTemplate5003PacketCodec)

    s2c<BattleSlotSwitchEventPacket>(0x3Eu, BattleSlotSwitchEventPacketCodec)

    c2s<BattleSimulationRequestPacket>(0x3Fu, BattleSimulationRequestPacketCodec)
    s2c<BattleMoveDisableUpdatePacket>(0x3Fu, BattleMoveDisableUpdatePacketCodec)

    c2s<SubmitBreedingPartyPacket>(0x40u, SubmitBreedingPartyPacketCodec)
    s2c<BattleSidePartyPacket>(0x40u, BattleSidePartyPacketCodec)

    c2s<MonsterFavoriteTogglePacket>(0x41u, MonsterFavoriteTogglePacketCodec)
    s2c<BattlePokemonSpritePacket>(0x41u, BattlePokemonSpritePacketCodec)

    c2s<LeaveChatChannelPacket>(0x42u, LeaveChatChannelPacketCodec)
    s2c<BattleSideAddPokemonPacket>(0x42u, BattleSideAddPokemonPacketCodec)

    c2s<SetChatUserIgnoredPacket>(0x43u, SetChatUserIgnoredPacketCodec)
    s2c<BattleSideRemovePokemonPacket>(0x43u, BattleSideRemovePokemonPacketCodec)

    c2s<TournamentTeleportAcceptPacket>(0x44u, TournamentTeleportAcceptPacketCodec)
    s2c<BattlePokemonStatusPacket>(0x44u, BattlePokemonStatusPacketCodec)

    c2s<NpcDialogueChoicePacket>(0x45u, NpcDialogueChoicePacketCodec)
    s2c<WorldStateControlPacket>(0x45u, WorldStateControlPacketCodec)

    c2s<BattleReadyPacket>(0x46u, BattleReadyPacketCodec)
    s2c<BattleControlBytesPacket>(0x46u, BattleControlBytesPacketCodec)

    c2s<LeaveMatchmakingQueuePacket>(0x47u, LeaveMatchmakingQueuePacketCodec)
    s2c<BattleScreenOpenPacket>(0x47u, BattleScreenOpenPacketCodec)

    c2s<BattleActionSubmitPacket>(0x48u, BattleActionSubmitPacketCodec)
    s2c<BattleActionResultPacket>(0x48u, BattleActionResultPacketCodec)

    c2s<JoinMatchmakingQueuePacket>(0x49u, JoinMatchmakingQueuePacketCodec)
    s2c<BattleMoveSelectionCancelPacket>(0x49u, BattleMoveSelectionCancelPacketCodec)

    c2s<BattleTierSelectPacket>(0x4Au, BattleTierSelectPacketCodec)
    s2c<BattleRatingBulkPacket>(0x4Au, BattleRatingBulkPacketCodec)

    c2s<BattleChatMessagePacket>(0x4Bu, BattleChatMessagePacketCodec)
    s2c<BattleRatingUpdatePacket>(0x4Bu, BattleRatingUpdatePacketCodec)

    c2s<MatchmakingActionPacket>(0x4Cu, MatchmakingActionPacketCodec)
    s2c<BattleMoveTargetsPacket>(0x4Cu, BattleMoveTargetsPacketCodec)

    c2s<MarketListingsRequestPacket>(0x4Du, MarketListingsRequestPacketCodec)
    s2c<BattleStateBytePacket>(0x4Du, BattleStateBytePacketCodec)

    s2c<BattleMoveListPacket>(0x4Eu, BattleMoveListPacketCodec)

    c2s<MarketSearchFilterPacket>(0x4Fu, MarketSearchFilterPacketCodec)
    s2c<ChannelAssignmentsPacket>(0x4Fu, ChannelAssignmentsPacketCodec)

    c2s<TradeActionPacket>(0x50u, TradeActionPacketCodec)
    s2c<DuelInvitePacket>(0x50u, DuelInvitePacketCodec)

    c2s<StringCommandPacket>(0x51u, StringCommandPacketCodec)
    s2c<DuelInviteOutcomePacket>(0x51u, DuelInviteOutcomePacketCodec)

    c2s<TradeSelectMonPacket>(0x52u, TradeSelectMonPacketCodec)
    s2c<TradeListEntryPacket>(0x52u, TradeListEntryPacketCodec)

    c2s<BattlePartySlotSelectPacket>(0x53u, BattlePartySlotSelectPacketCodec)
    s2c<BattleStateSlotIntPacket>(0x53u, BattleStateSlotIntPacketCodec)

    s2c<BattleBoardCellPacket>(0x54u, BattleBoardCellPacketCodec)

    s2c<SpatialGroupSnapshotPacket>(0x55u, SpatialGroupSnapshotPacketCodec)

    s2c<SpatialGroupInsertPacket>(0x56u, SpatialGroupInsertPacketCodec)

    s2c<SpatialGroupDeletePacket>(0x57u, SpatialGroupDeletePacketCodec)

    s2c<MenuVisibilityPacket>(0x58u, MenuVisibilityPacketCodec)

    s2c<MenuPagePayloadPacket>(0x59u, MenuPagePayloadPacketCodec)

    s2c<EntityNameChoicesPacket>(0x5Au, EntityNameChoicesPacketCodec)

    s2c<OptionListWindowPacket>(0x5Bu, OptionListWindowPacketCodec)

    s2c<ListWindowPagePacket>(0x5Cu, ListWindowPagePacketCodec)

    s2c<MarketBoardPagePacket>(0x5Du, MarketBoardPagePacketCodec)

    s2c<SceneObjectStatesPacket>(0x5Eu, SceneObjectStatesPacketCodec)

    s2c<StorageInventoryPacket>(0x5Fu, StorageInventoryPacketCodec)

    c2s<BlockPlayerPacket>(0x60u, BlockPlayerPacketCodec)
    s2c<StorageContextWindowPacket>(0x60u, StorageContextWindowPacketCodec)

    c2s<UnblockPlayerPacket>(0x61u, UnblockPlayerPacketCodec)
    s2c<EntityCoordSyncPacket>(0x61u, EntityCoordSyncPacketCodec)

    c2s<AddFriendPacket>(0x62u, AddFriendPacketCodec)
    s2c<SocialActionOutcomePacket>(0x62u, SocialActionOutcomePacketCodec)

    c2s<RemoveFriendPacket>(0x63u, RemoveFriendPacketCodec)
    s2c<FriendListPacket>(0x63u, FriendListPacketCodec)

    c2s<FriendProfileRequestPacket>(0x64u, FriendProfileRequestPacketCodec)
    s2c<ContactInsertPacket>(0x64u, ContactInsertPacketCodec)

    s2c<ContactDeletePacket>(0x65u, ContactDeletePacketCodec)

    s2c<ContactOnlineStatePacket>(0x66u, ContactOnlineStatePacketCodec)

    s2c<PartyRosterPacket>(0x67u, PartyRosterPacketCodec)

    s2c<PartyMemberJoinPacket>(0x68u, PartyMemberJoinPacketCodec)

    s2c<PartyMemberLeavePacket>(0x69u, PartyMemberLeavePacketCodec)

    c2s<InstanceReadyCheckPacket>(0x6Au, InstanceReadyCheckPacketCodec)

    c2s<CancelMatchmakingSearchPacket>(0x6Bu, CancelMatchmakingSearchPacketCodec)
    s2c<ActiveBattleSidePacket>(0x6Bu, ActiveBattleSidePacketCodec)

    c2s<MatchmakingLanguagePrefsPacket>(0x6Cu, MatchmakingLanguagePrefsPacketCodec)
    s2c<MountMoveCooldownPacket>(0x6Cu, MountMoveCooldownPacketCodec)

    s2c<ClientCookieUpdatePacket>(0x6Du, ClientCookieUpdatePacketCodec)

    s2c<WorldToggleFlagsPacket>(0x6Eu, WorldToggleFlagsPacketCodec)

    s2c<NamedCategoryEntriesPacket>(0x6Fu, NamedCategoryEntriesPacketCodec)

    c2s<StorageBoxRequestPacket>(0x70u, StorageBoxRequestPacketCodec)
    s2c<GtlMarketListingsPacket>(0x70u, GtlMarketListingsPacketCodec)

    c2s<GtlPurchaseListingPacket>(0x71u, GtlPurchaseListingPacketCodec)
    s2c<MatchmakingRentalsPacket>(0x71u, MatchmakingRentalsPacketCodec)

    s2c<WorldStateValuePacket>(0x72u, WorldStateValuePacketCodec)

    c2s<AssignBreedingSlotPacket>(0x73u, AssignBreedingSlotPacketCodec)
    s2c<BreedingForecastPacket>(0x73u, BreedingForecastPacketCodec)

    c2s<LeaderboardViewRequestPacket>(0x74u, LeaderboardViewRequestPacketCodec)
    s2c<ServerNoticePacket>(0x74u, ServerNoticePacketCodec)

    c2s<TourneyRegistrationPacket>(0x75u, TourneyRegistrationPacketCodec)
    s2c<MatchmakingSideTeamPacket>(0x75u, MatchmakingSideTeamPacketCodec)

    c2s<NpcInteractionChoicePacket>(0x76u, NpcInteractionChoicePacketCodec)
    s2c<ActivePokemonSummaryPacket>(0x76u, ActivePokemonSummaryPacketCodec)

    c2s<BattleTargetPickPacket>(0x77u, BattleTargetPickPacketCodec)
    s2c<StreamChunkPacket>(0x77u, StreamChunkPacketCodec)

    s2c<MatchmakingMemberProgressPacket>(0x78u, MatchmakingMemberProgressPacketCodec)

    c2s<PcBoxStorePacket>(0x79u, PcBoxStorePacketCodec)
    s2c<BattleStatCountersPacket>(0x79u, BattleStatCountersPacketCodec)

    c2s<PcBoxRenamePacket>(0x7Au, PcBoxRenamePacketCodec)
    s2c<LearnedMovesetPacket>(0x7Au, LearnedMovesetPacketCodec)

    c2s<CoopScoreboardRequestPacket>(0x7Bu, CoopScoreboardRequestPacketCodec)
    s2c<TournamentMatchupsPacket>(0x7Bu, TournamentMatchupsPacketCodec)

    c2s<GtlListingSearchPacket>(0x7Cu, GtlListingSearchPacketCodec)
    s2c<GtlItemMarketPacket>(0x7Cu, GtlItemMarketPacketCodec)

    s2c<GameShopCatalogPacket>(0x7Du, GameShopCatalogPacketCodec)

    s2c<EggIncubatorSlotsPacket>(0x7Eu, EggIncubatorSlotsPacketCodec)

    s2c<GtlPriceHistoryPacket>(0x7Fu, GtlPriceHistoryPacketCodec)

    c2s<TeamFoundPacket>(0x80u, TeamFoundPacketCodec)
    s2c<GuildMembershipPacket>(0x80u, GuildMembershipPacketCodec)

    c2s<GuildMotdUpdatePacket>(0x81u, GuildMotdUpdatePacketCodec)
    s2c<GuildProfileSyncPacket>(0x81u, GuildProfileSyncPacketCodec)

    c2s<GuildDepartPacket>(0x82u, GuildDepartPacketCodec)

    c2s<GuildChatSendPacket>(0x83u, GuildChatSendPacketCodec)
    s2c<GuildRosterAddPacket>(0x83u, GuildRosterAddPacketCodec)

    c2s<GuildMemberRankAssignPacket>(0x84u, GuildMemberRankAssignPacketCodec)
    s2c<GuildMemberRankChangePacket>(0x84u, GuildMemberRankChangePacketCodec)

    c2s<GuildMemberExpelPacket>(0x85u, GuildMemberExpelPacketCodec)
    s2c<GuildRosterRemovePacket>(0x85u, GuildRosterRemovePacketCodec)

    c2s<GuildRankPermissionUpdatePacket>(0x86u, GuildRankPermissionUpdatePacketCodec)
    s2c<GuildMemberPresencePacket>(0x86u, GuildMemberPresencePacketCodec)

    c2s<GuildDisbandToggleRequestPacket>(0x87u, GuildDisbandToggleRequestPacketCodec)
    s2c<ContactCategoryGroupPacket>(0x87u, ContactCategoryGroupPacketCodec)

    c2s<GuildRankLabelUpdatePacket>(0x88u, GuildRankLabelUpdatePacketCodec)
    s2c<GuildRosterBulkPacket>(0x88u, GuildRosterBulkPacketCodec)

    c2s<GuildActivityLogPageRequestPacket>(0x89u, GuildActivityLogPageRequestPacketCodec)
    s2c<GuildActivityLogPacket>(0x89u, GuildActivityLogPacketCodec)

    c2s<MailAttachmentRequestPacket>(0x8Au, MailAttachmentRequestPacketCodec)

    s2c<MonsterRecordBookPacket>(0x8Bu, MonsterRecordBookPacketCodec)

    s2c<ChatChannelMessagesPacket>(0x8Cu, ChatChannelMessagesPacketCodec)

    s2c<ChatChannelUserListPacket>(0x8Du, ChatChannelUserListPacketCodec)

    s2c<ChannelCountDeltaPacket>(0x8Eu, ChannelCountDeltaPacketCodec)

    s2c<ChatMessageWithdrawPacket>(0x8Fu, ChatMessageWithdrawPacketCodec)

    c2s<OverworldPathToTilePacket>(0x90u, OverworldPathToTilePacketCodec)
    s2c<EntitySpriteChangePacket>(0x90u, EntitySpriteChangePacketCodec)

    c2s<OverworldStepMovePacket>(0x91u, OverworldStepMovePacketCodec)
    s2c<OpenAppearanceEditorPacket>(0x91u, OpenAppearanceEditorPacketCodec)

    c2s<BattleTransitionReadyPacket>(0x92u, BattleTransitionReadyPacketCodec)
    s2c<AppearanceRequestResultPacket>(0x92u, AppearanceRequestResultPacketCodec)

    c2s<PendingRequestCancelPacket>(0x93u, PendingRequestCancelPacketCodec)
    s2c<BattleStatStagePacket>(0x93u, BattleStatStagePacketCodec)

    c2s<PartyInfoRequestPacket>(0x94u, PartyInfoRequestPacketCodec)
    s2c<EntityAppearanceSyncPacket>(0x94u, EntityAppearanceSyncPacketCodec)

    c2s<MailComposeSendPacket>(0x95u, MailComposeSendPacketCodec)
    s2c<BattleSwitchPromptTogglePacket>(0x95u, BattleSwitchPromptTogglePacketCodec)

    c2s<TeamPreviewRevealPickPacket>(0x96u, TeamPreviewRevealPickPacketCodec)
    s2c<BattleEndDisconnectPacket>(0x96u, BattleEndDisconnectPacketCodec)

    c2s<TeamPreviewSidePickPacket>(0x97u, TeamPreviewSidePickPacketCodec)
    s2c<BattleTeamPreviewListPacket>(0x97u, BattleTeamPreviewListPacketCodec)

    c2s<TeamPreviewLeadSubmitPacket>(0x98u, TeamPreviewLeadSubmitPacketCodec)
    s2c<WorldVersionPacket>(0x98u, WorldVersionPacketCodec)

    c2s<BattleTeamPreviewConfirmPacket>(0x99u, BattleTeamPreviewConfirmPacketCodec)
    s2c<BattleSendOutActivePacket>(0x99u, BattleSendOutActivePacketCodec)

    c2s<CreateMarketListingPacket>(0x9Au, CreateMarketListingPacketCodec)
    s2c<BattleSlotAnimationResetPacket>(0x9Au, BattleSlotAnimationResetPacketCodec)

    c2s<GtlSellPokemonListingPacket>(0x9Bu, GtlSellPokemonListingPacketCodec)
    s2c<BattleActionOutcomePacket>(0x9Bu, BattleActionOutcomePacketCodec)

    c2s<GtlConfirmPurchasePacket>(0x9Cu, GtlConfirmPurchasePacketCodec)
    s2c<BattleDisconnectMessagePacket>(0x9Cu, BattleDisconnectMessagePacketCodec)

    c2s<SelectSinglePokemonPacket>(0x9Du, SelectSinglePokemonPacketCodec)
    s2c<BattleActionPromptTogglePacket>(0x9Du, BattleActionPromptTogglePacketCodec)

    c2s<SelectPartyPokemonPacket>(0x9Eu, SelectPartyPokemonPacketCodec)
    s2c<EntityEmoteBubblePacket>(0x9Eu, EntityEmoteBubblePacketCodec)

    c2s<GtlTradeLogRequestPacket>(0x9Fu, GtlTradeLogRequestPacketCodec)

    c2s<PlayerChatInputPacket>(0xA0u, PlayerChatInputPacketCodec)
    s2c<FriendRosterPacket>(0xA0u, FriendRosterPacketCodec)

    c2s<GtlListingCancelPacket>(0xA1u, GtlListingCancelPacketCodec)
    s2c<GmPlayerLookupPacket>(0xA1u, GmPlayerLookupPacketCodec)

    c2s<AdminNoteActionPacket>(0xA2u, AdminNoteActionPacketCodec)
    s2c<GmPanelVariantPacket>(0xA2u, GmPanelVariantPacketCodec)

    c2s<GtlPurchasePacket>(0xA3u, GtlPurchasePacketCodec)
    s2c<EntityNameVisibilityPacket>(0xA3u, EntityNameVisibilityPacketCodec)

    c2s<GtlListingActionPacket>(0xA4u, GtlListingActionPacketCodec)
    s2c<HighScoreBoardPacket>(0xA4u, HighScoreBoardPacketCodec)

    c2s<GtlOpenSessionPacket>(0xA5u, GtlOpenSessionPacketCodec)
    s2c<WorldNoticeSlotPacket>(0xA5u, WorldNoticeSlotPacketCodec)

    c2s<DataDigestSyncResponsePacket>(0xA6u, DataDigestSyncResponsePacketCodec)
    s2c<DataDigestSyncPacket>(0xA6u, DataDigestSyncPacketCodec)

    c2s<DataContentSyncBatchPacket>(0xA7u, DataContentSyncBatchPacketCodec)
    s2c<DataDigestSyncBatchedPacket>(0xA7u, DataDigestSyncBatchedPacketCodec)

    c2s<BatchInputEventsPacket>(0xA8u, BatchInputEventsPacketCodec)
    s2c<SelectionListWindowOpenPacket>(0xA8u, SelectionListWindowOpenPacketCodec)

    c2s<ModerationActionConfirmPacket>(0xA9u, ModerationActionConfirmPacketCodec)
    s2c<EntityAttributeChangePacket>(0xA9u, EntityAttributeChangePacketCodec)

    s2c<WorldFlagStateUpdatePacket>(0xAAu, WorldFlagStateUpdatePacketCodec)

    s2c<ChunkedTransferBeginPacket>(0xABu, ChunkedTransferBeginPacketCodec)

    s2c<ChunkedTransferAppendPacket>(0xACu, ChunkedTransferAppendPacketCodec)

    s2c<WorldTimedEventStatusPacket>(0xADu, WorldTimedEventStatusPacketCodec)

    s2c<GroupMemberRosterPacket>(0xAEu, GroupMemberRosterPacketCodec)

    s2c<SceneEntityActionDispatchPacket>(0xB0u, SceneEntityActionDispatchPacketCodec)

    s2c<SocialProfileDialogOpenPacket>(0xB1u, SocialProfileDialogOpenPacketCodec)

    bidi<NpcAnimationPacket>(0xB2u, NpcAnimationPacketCodec)

    s2c<MapTileAttributeSetPacket>(0xB3u, MapTileAttributeSetPacketCodec)

    bidi<RenderScreenPacket>(0xB4u, RenderScreenPacketCodec)

    s2c<EntityAppearancePatchPacket>(0xB5u, EntityAppearancePatchPacketCodec)

    s2c<WorldActionDispatchPacket>(0xB6u, WorldActionDispatchPacketCodec)

    s2c<EntityDespawnPacket>(0xB7u, EntityDespawnPacketCodec)

    s2c<MapTileEntityStateSetPacket>(0xB8u, MapTileEntityStateSetPacketCodec)

    bidi<MapTransitionAckPacket>(0xB9u, MapTransitionAckPacketCodec)

    s2c<MapTileObjectSlotSetPacket>(0xBAu, MapTileObjectSlotSetPacketCodec)

    s2c<CameraFollowEntityPacket>(0xBBu, CameraFollowEntityPacketCodec)

    s2c<MapTileObjectRepositionPacket>(0xBCu, MapTileObjectRepositionPacketCodec)

    s2c<WorldEntityStateResetPacket>(0xBDu, WorldEntityStateResetPacketCodec)

    s2c<WorldOverlayObjectSetPacket>(0xBEu, WorldOverlayObjectSetPacketCodec)

    s2c<MapTileAnimationTogglePacket>(0xC0u, MapTileAnimationTogglePacketCodec)

    c2s<TournamentRegistrationPacket>(0xC1u, TournamentRegistrationPacketCodec)
    s2c<MapWeatherModeSetPacket>(0xC1u, MapWeatherModeSetPacketCodec)

    bidi<KeepAlivePacket>(0xC2u, KeepAlivePacketCodec)

    s2c<FieldMapTilePaletteApplyPacket>(0xC3u, FieldMapTilePaletteApplyPacketCodec)

    s2c<FieldMapBattleSlotTileMapPacket>(0xC4u, FieldMapBattleSlotTileMapPacketCodec)

    s2c<FieldMapBattleSlotMarkerPacket>(0xC5u, FieldMapBattleSlotMarkerPacketCodec)

    s2c<WorldObjectInstanceDespawnPacket>(0xC8u, WorldObjectInstanceDespawnPacketCodec)

    s2c<BattleStartScenePacket>(0xCAu, BattleStartScenePacketCodec)

    c2s<SendChatCommandPacket>(0xD0u, SendChatCommandPacketCodec)
    s2c<EntityGroupSnapshotPacket>(0xD0u, EntityGroupSnapshotPacketCodec)

    c2s<LinkKickMemberPacket>(0xD1u, LinkKickMemberPacketCodec)
    s2c<EntityGroupMemberAddPacket>(0xD1u, EntityGroupMemberAddPacketCodec)

    s2c<EntityGroupMemberRemovePacket>(0xD2u, EntityGroupMemberRemovePacketCodec)

    c2s<CancelSocialInteractionPacket>(0xD3u, CancelSocialInteractionPacketCodec)
    s2c<ObjectiveProgressBulkPacket>(0xD3u, ObjectiveProgressBulkPacketCodec)

    c2s<RequestSocialProfilePacket>(0xD4u, RequestSocialProfilePacketCodec)
    s2c<ObjectiveProgressPacket>(0xD4u, ObjectiveProgressPacketCodec)

    s2c<TrackedEntitySlotsPacket>(0xD5u, TrackedEntitySlotsPacketCodec)

    s2c<MenuPromptOpenPacket>(0xD6u, MenuPromptOpenPacketCodec)

    s2c<EntityChecklistPromptPacket>(0xD7u, EntityChecklistPromptPacketCodec)

    s2c<MenuPromptClosePacket>(0xD8u, MenuPromptClosePacketCodec)

    s2c<RequestConfirmationPromptPacket>(0xD9u, RequestConfirmationPromptPacketCodec)

    s2c<EntityPanelSelectPacket>(0xDAu, EntityPanelSelectPacketCodec)

    s2c<EntityFramesUpdatePacket>(0xDBu, EntityFramesUpdatePacketCodec)

    s2c<CategoryFlagsPacket>(0xDCu, CategoryFlagsPacketCodec)

    c2s<SaveTemplateEntryPacket>(0xE0u, SaveTemplateEntryPacketCodec)

    c2s<GtlCreateListingPacket>(0xE1u, GtlCreateListingPacketCodec)

    c2s<GtlSearchFilterPacket>(0xE2u, GtlSearchFilterPacketCodec)

    c2s<BattleRewardSelectPacket>(0xE3u, BattleRewardSelectPacketCodec)

    c2s<BattleChallengeRequestPacket>(0xE4u, BattleChallengeRequestPacketCodec)

    s2c<EntityMovePacket>(0xEAu, EntityMovePacketCodec)

    s2c<QueuePositionPacket>(0xF0u, QueuePositionPacketCodec)

    c2s<ChunkedTransferDataPacket>(0xF1u, ChunkedTransferDataPacketCodec)
    s2c<ViewScalePacket>(0xF1u, ViewScalePacketCodec)

    s2c<SessionExpiryInitPacket>(0xF2u, SessionExpiryInitPacketCodec)

    s2c<LocalPlayerStatePacket>(0xF3u, LocalPlayerStatePacketCodec)

    s2c<CurrencyBalancePacket>(0xF4u, CurrencyBalancePacketCodec)

    s2c<ServerMessagePacket>(0xF5u, ServerMessagePacketCodec)

    s2c<ChunkedImagePacket>(0xF6u, ChunkedImagePacketCodec)

    s2c<GmPanelEntryPacket>(0xF7u, GmPanelEntryPacketCodec)

    s2c<DisconnectNoticePacket>(0xF8u, DisconnectNoticePacketCodec)

    s2c<RosterCooldownPacket>(0xF9u, RosterCooldownPacketCodec)

    s2c<CancelEntityMessagePacket>(0xFAu, CancelEntityMessagePacketCodec)

    s2c<PlayerInputLockPacket>(0xFBu, PlayerInputLockPacketCodec)

    s2c<ServerEndpointListPacket>(0xFCu, ServerEndpointListPacketCodec)

    s2c<GtlListingsPagePacket>(0xFEu, GtlListingsPagePacketCodec)

    s2c<WorldSessionStatePacket>(0xFFu, WorldSessionStatePacketCodec)
  }
}

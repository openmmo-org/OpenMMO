package de.fiereu.openmmo.server.game.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import de.fiereu.openmmo.server.game.services.command.CatchCommand
import de.fiereu.openmmo.server.game.services.command.ChatCommand
import de.fiereu.openmmo.server.game.services.command.HelpCommand
import de.fiereu.openmmo.server.game.services.command.PosCommand
import de.fiereu.openmmo.server.game.services.command.TestBattleCommand

/**
 * A name must not start with a client side command. The client resolves those itself and never
 * sends them, which is why there is a /pos and no /where, which /w would have swallowed.
 */
@Module
interface ChatCommandModule {
  @Binds @IntoSet fun helpCommand(command: HelpCommand): ChatCommand

  @Binds @IntoSet fun posCommand(command: PosCommand): ChatCommand

  @Binds @IntoSet fun testBattleCommand(command: TestBattleCommand): ChatCommand

  @Binds @IntoSet fun catchCommand(command: CatchCommand): ChatCommand
}

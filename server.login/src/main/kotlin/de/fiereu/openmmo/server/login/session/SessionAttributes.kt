package de.fiereu.openmmo.server.login.session

import de.fiereu.network.SessionAttribute

/** The account id a login session authenticated as, sent on to the game server when it joins. */
val AUTHED_USER_ID = SessionAttribute.of<Int>("authedUserId")

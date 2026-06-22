package de.fiereu.openmmo.server.game.storage

import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialStore @Inject constructor() {
  private val friendsByUser = ConcurrentHashMap<Int, MutableSet<String>>()
  private val blockedByUser = ConcurrentHashMap<Int, MutableSet<String>>()

  fun getFriends(userId: Int): Set<String> = friendsByUser.getOrPut(userId) { seedFriends() }

  fun addFriend(userId: Int, name: String) {
    friendsByUser.getOrPut(userId) { seedFriends() }.add(name)
  }

  fun removeFriend(userId: Int, name: String): Boolean =
      friendsByUser.getOrPut(userId) { seedFriends() }.remove(name)

  fun getBlocked(userId: Int): Set<String> = blockedByUser.getOrPut(userId) { mutableSetOf() }

  fun block(userId: Int, name: String) {
    blockedByUser.getOrPut(userId) { mutableSetOf() }.add(name)
  }

  fun unblock(userId: Int, name: String): Boolean =
      blockedByUser.getOrPut(userId) { mutableSetOf() }.remove(name)

  private fun seedFriends(): MutableSet<String> = linkedSetOf("Red", "Blue", "Green")
}

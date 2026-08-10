package de.fiereu.openmmo.launcher.client

import de.fiereu.openmmo.launcher.xml.SecureXml
import org.w3c.dom.Element

object FeedParser {

  fun parseMain(bytes: ByteArray): MainFeed {
    val root = parse(bytes, "main_feed")
    val revision = root.intOf("revision") ?: error("main feed has no revision")
    return MainFeed(
        loginHost = root.textOf("ip") ?: error("main feed has no ip"),
        loginPort = root.intOf("port") ?: error("main feed has no port"),
        revision = revision,
        minRevision = root.intOf("min_revision") ?: 0,
    )
  }

  // A malformed entry is dropped on its own, not the whole feed.
  fun parseUpdate(bytes: ByteArray): UpdateFeed =
      UpdateFeed(SecureXml.elements(parse(bytes, "update_feed"), "file").mapNotNull(::readFile))

  private fun readFile(element: Element): RemoteFile? {
    val name = sanitize(element.getAttribute("name")) ?: return null
    val sha256 = element.getAttribute("sha256")
    if (sha256.length != 64 || !sha256.all { it.isDigit() || it in 'a'..'f' }) return null
    val size = element.getAttribute("size").toLongOrNull() ?: return null
    if (size <= 0) return null
    return RemoteFile(
        name = name,
        sha256 = sha256,
        size = size,
        os = element.getAttribute("os"),
        arch = element.getAttribute("arch"),
        executable = element.getAttribute("executable").toBoolean(),
        onlyIfNotExists = element.getAttribute("only_if_not_exists").toBoolean(),
    )
  }

  // Feed names are attacker controlled the moment a mirror is compromised.
  fun sanitize(name: String): String? {
    if (name.isEmpty()) return null
    val normalized = name.replace('\\', '/')
    if (normalized.startsWith("/") || normalized.contains(':')) return null
    val parts = normalized.split('/')
    if (parts.any { it.isEmpty() || it == "." || it == ".." }) return null
    return parts.joinToString("/")
  }

  private fun parse(bytes: ByteArray, rootName: String): Element = SecureXml.root(bytes, rootName)

  private fun Element.textOf(tag: String): String? =
      getElementsByTagName(tag).item(0)?.textContent?.trim()?.takeIf { it.isNotEmpty() }

  private fun Element.intOf(tag: String): Int? = textOf(tag)?.toIntOrNull()
}

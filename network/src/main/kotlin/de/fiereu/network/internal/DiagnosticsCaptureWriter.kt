package de.fiereu.network.internal

import io.netty.buffer.ByteBuf
import java.io.BufferedWriter
import java.io.File
import java.util.concurrent.ConcurrentHashMap

/**
 * Shared best-effort line-appender for the S2C (CompressionEncoder) and C2S (ProtocolHandler)
 * diagnostic capture taps. Dev-diagnostics-only: used to byte-diff our server's wire traffic
 * against golden real-PokeMMO captures during local, burner-account clean-room protocol reversing.
 * Both taps are gated by [de.fiereu.network.PipelineOptions.diagnosticsCaptureEnabled] (off by
 * default and in production) and write under a configurable directory, never a hard-coded path.
 */
internal object DiagnosticsCaptureWriter {
  private val writers = ConcurrentHashMap<String, BufferedWriter>()

  fun appendLine(dir: String, fileName: String, line: String) {
    try {
      val writer =
          writers.getOrPut("$dir/$fileName") {
            val directory = File(dir)
            directory.mkdirs()
            File(directory, fileName).bufferedWriter()
          }
      synchronized(writer) {
        writer.write(line)
        writer.newLine()
        writer.flush()
      }
    } catch (_: Exception) {
      // Capture is best-effort diagnostics; never let it break the actual encode/decode path.
    }
  }

  fun hexOf(buf: ByteBuf, index: Int, len: Int): String {
    val sb = StringBuilder(len * 2)
    for (i in 0 until len) {
      val b = buf.getByte(index + i).toInt() and 0xFF
      sb.append("0123456789abcdef"[b ushr 4])
      sb.append("0123456789abcdef"[b and 0x0F])
    }
    return sb.toString()
  }
}

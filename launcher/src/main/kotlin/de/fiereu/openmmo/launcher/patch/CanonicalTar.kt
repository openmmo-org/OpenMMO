package de.fiereu.openmmo.launcher.patch

import de.fiereu.openmmo.launcher.client.RemoteFile
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.EOFException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.PosixFilePermission

object CanonicalTar {

  private const val BLOCK_SIZE = 512
  private const val RECORD_SIZE = 10240

  /**
   * Creates a canonical USTAR archive matching GNU tar: `--format=ustar --numeric-owner --owner=0
   * --group=0 --mtime=@0 --no-recursion`
   *
   * Entries are written sorted by name with normalized timestamps and permissions.
   */
  fun create(sourceDir: Path, files: List<RemoteFile>, outTar: Path) {
    outTar.parent?.let(Files::createDirectories)
    val sortedFiles = files.sortedBy { it.name }

    var totalBytesWritten = 0L

    BufferedOutputStream(Files.newOutputStream(outTar)).use { out ->
      val buffer = ByteArray(65536)

      for (fileEntry in sortedFiles) {
        val filePath = sourceDir.resolve(fileEntry.name)
        if (!Files.isRegularFile(filePath)) {
          error("File ${fileEntry.name} missing from source directory $sourceDir")
        }

        val size = Files.size(filePath)
        val isExecutable = fileEntry.executable

        val header = createHeader(fileEntry.name, size, isExecutable)
        out.write(header)
        totalBytesWritten += BLOCK_SIZE

        BufferedInputStream(Files.newInputStream(filePath)).use { input ->
          var remaining = size
          while (remaining > 0) {
            val toRead = minOf(buffer.size.toLong(), remaining).toInt()
            val chunkRead = input.read(buffer, 0, toRead)
            if (chunkRead == -1) break
            out.write(buffer, 0, chunkRead)
            totalBytesWritten += chunkRead
            remaining -= chunkRead
          }
        }

        val pad = ((BLOCK_SIZE - (size % BLOCK_SIZE)) % BLOCK_SIZE).toInt()
        if (pad > 0) {
          out.write(ByteArray(pad))
          totalBytesWritten += pad
        }
      }

      // GNU tar writes at least 2 zero blocks (1024 bytes) and pads to 10240-byte record boundary
      val minTotal = totalBytesWritten + 1024L
      val alignedTotal = ((minTotal + RECORD_SIZE - 1) / RECORD_SIZE) * RECORD_SIZE
      val endPadding = (alignedTotal - totalBytesWritten).toInt()
      out.write(ByteArray(endPadding))
      out.flush()
    }
  }

  /** Extracts a USTAR archive to the target directory, restoring executable permissions. */
  fun extract(tarPath: Path, targetDir: Path) {
    Files.createDirectories(targetDir)
    BufferedInputStream(Files.newInputStream(tarPath)).use { input ->
      val header = ByteArray(BLOCK_SIZE)
      val buffer = ByteArray(65536)

      while (true) {
        val read = readFully(input, header)
        if (read < BLOCK_SIZE || isZeroBlock(header)) {
          break
        }

        val name = parseString(header, 0, 100)
        if (name.isEmpty()) break

        val size = parseOctal(header, 124, 12)
        val mode = parseOctal(header, 100, 8).toInt()
        val isExecutable = (mode and 0b001_001_001) != 0

        val destFile = targetDir.resolve(name).normalize()
        if (!destFile.startsWith(targetDir.normalize())) {
          error("Tar entry $name attempts path traversal outside target directory")
        }

        destFile.parent?.let(Files::createDirectories)

        BufferedOutputStream(Files.newOutputStream(destFile)).use { out ->
          var remaining = size
          while (remaining > 0) {
            val toRead = minOf(buffer.size.toLong(), remaining).toInt()
            val chunkRead = input.read(buffer, 0, toRead)
            if (chunkRead == -1) throw EOFException("Unexpected EOF while reading entry $name")
            out.write(buffer, 0, chunkRead)
            remaining -= chunkRead
          }
        }

        if (isExecutable) {
          markExecutable(destFile)
        }

        val pad = ((BLOCK_SIZE - (size % BLOCK_SIZE)) % BLOCK_SIZE).toInt()
        if (pad > 0) {
          skipFully(input, pad.toLong())
        }
      }
    }
  }

  private fun createHeader(name: String, size: Long, isExecutable: Boolean): ByteArray {
    val header = ByteArray(BLOCK_SIZE)
    val nameBytes = name.toByteArray(StandardCharsets.UTF_8)
    require(nameBytes.size <= 100) { "Filename $name exceeds 100 bytes USTAR limit" }
    System.arraycopy(nameBytes, 0, header, 0, nameBytes.size)

    val modeStr =
        String.format("%07o", if (isExecutable) 0b111_101_101 else 0b110_100_100) + "\u0000"
    writeAscii(header, 100, modeStr)

    writeAscii(header, 108, "0000000\u0000") // uid
    writeAscii(header, 116, "0000000\u0000") // gid

    val sizeStr = String.format("%011o", size) + "\u0000"
    writeAscii(header, 124, sizeStr)

    writeAscii(header, 136, "00000000000\u0000") // mtime = 0

    header[156] = '0'.code.toByte() // typeflag = regular file

    // magic and version: "ustar\0" and "00"
    header[257] = 'u'.code.toByte()
    header[258] = 's'.code.toByte()
    header[259] = 't'.code.toByte()
    header[260] = 'a'.code.toByte()
    header[261] = 'r'.code.toByte()
    header[262] = 0.toByte()
    header[263] = '0'.code.toByte()
    header[264] = '0'.code.toByte()

    // calculate checksum with chksum field set to 8 spaces (0x20)
    for (i in 148 until 156) {
      header[i] = ' '.code.toByte()
    }

    var sum = 0
    for (b in header) {
      sum += (b.toInt() and 0xFF)
    }

    val chksumStr = String.format("%06o", sum) + "\u0000 "
    writeAscii(header, 148, chksumStr)

    return header
  }

  private fun writeAscii(header: ByteArray, offset: Int, text: String) {
    val bytes = text.toByteArray(StandardCharsets.US_ASCII)
    System.arraycopy(bytes, 0, header, offset, bytes.size)
  }

  private fun parseString(header: ByteArray, offset: Int, length: Int): String {
    var end = offset
    val max = offset + length
    while (end < max && header[end] != 0.toByte()) {
      end++
    }
    return String(header, offset, end - offset, StandardCharsets.UTF_8)
  }

  private fun parseOctal(header: ByteArray, offset: Int, length: Int): Long {
    val str = parseString(header, offset, length).trim().trim('\u0000')
    if (str.isEmpty()) return 0L
    return str.toLong(8)
  }

  private fun isZeroBlock(block: ByteArray): Boolean {
    for (b in block) {
      if (b != 0.toByte()) return false
    }
    return true
  }

  private fun readFully(input: InputStream, buffer: ByteArray): Int {
    var totalRead = 0
    while (totalRead < buffer.size) {
      val read = input.read(buffer, totalRead, buffer.size - totalRead)
      if (read == -1) break
      totalRead += read
    }
    return totalRead
  }

  private fun skipFully(input: InputStream, bytesToSkip: Long) {
    var remaining = bytesToSkip
    while (remaining > 0) {
      val skipped = input.skip(remaining)
      if (skipped <= 0) {
        if (input.read() == -1) throw EOFException("Unexpected EOF while skipping padding")
        remaining -= 1
      } else {
        remaining -= skipped
      }
    }
  }

  private fun markExecutable(file: Path) {
    runCatching { file.toFile().setExecutable(true, false) }
    runCatching {
      val perms = Files.getPosixFilePermissions(file).toMutableSet()
      perms.add(PosixFilePermission.OWNER_EXECUTE)
      perms.add(PosixFilePermission.GROUP_EXECUTE)
      perms.add(PosixFilePermission.OTHERS_EXECUTE)
      Files.setPosixFilePermissions(file, perms)
    }
  }
}

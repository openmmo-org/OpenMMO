package de.fiereu.network

object PipelineNames {
  const val WRITE_TIMEOUT = "write-timeout"
  const val FRAME_LOGGER = "frame-logger"
  const val FRAME_DECODER = "frame-decoder"
  const val FRAME_ENCODER = "frame-encoder"
  const val CHECKSUM_DECODER = "checksum-decoder"
  const val CHECKSUM_ENCODER = "checksum-encoder"
  const val CIPHER_DECODER = "cipher-decoder"
  const val CIPHER_ENCODER = "cipher-encoder"
  const val COMPRESSION_DECODER = "compression-decoder"
  const val COMPRESSION_ENCODER = "compression-encoder"
  const val PROTOCOL_LOGGER = "protocol-logger"
  const val PROTOCOL_HANDLER = "protocol-handler"
}

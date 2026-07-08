package de.fiereu.network

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

data class PipelineOptions(
    val checksumSize: Int = 16,
    val writeTimeout: Duration = 25.minutes,
    val maxFrameLength: Int = 0xFFFF,
    val compressionThreshold: Int = 256,
    val maxHelloSkew: Duration = 10.seconds,
    val frameLogging: Boolean = false,
    /**
     * Dev-diagnostics-only: gates the S2C (CompressionEncoder) and C2S (ProtocolHandler) capture
     * taps used for local, burner-account clean-room protocol reversing (byte-diffing our wire
     * traffic against golden real-PokeMMO captures). Off by default and must stay off in production
     * -- on purpose only enabled by dev/local config. Never wired for LoginProtocol (see
     * LoginServer.kt), so login/session-credential traffic is never captured regardless of this
     * flag.
     */
    val diagnosticsCaptureEnabled: Boolean = false,
    val diagnosticsCaptureDir: String = "captures",
)

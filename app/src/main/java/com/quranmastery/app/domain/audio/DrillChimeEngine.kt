package com.quranmastery.app.domain.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import kotlin.math.PI
import kotlin.math.sin

/**
 * Pure constants for drill chimes. Unit-testable on the JVM without Android.
 */
object ChimeSpec {
    const val START_FREQ: Int = 880
    const val EYES_CLOSED_FREQ: Int = 660
    const val END_FREQ: Int = 440
    const val COMPLETE_FREQ: Int = 523

    const val START_MS: Int = 300
    const val EYES_CLOSED_MS: Int = 300
    const val COMPLETE_MS: Int = 500

    /** Stare duration used by drill steps. */
    const val STARE_MS: Long = 2000L

    const val SAMPLE_RATE: Int = 22050

    /** Synthesize a mono 16-bit PCM sine tone. Pure function, JVM-testable. */
    fun synthesizeTone(freqHz: Int, durationMs: Int, sampleRate: Int = SAMPLE_RATE): ShortArray {
        require(freqHz > 0) { "freqHz must be > 0" }
        require(durationMs > 0) { "durationMs must be > 0" }
        val frames = (durationMs * sampleRate / 1000).coerceAtLeast(1)
        val out = ShortArray(frames)
        // Short linear fade in/out (10ms) to avoid clicks.
        val fadeFrames = (10 * sampleRate / 1000).coerceAtMost(frames / 2).coerceAtLeast(1)
        for (i in 0 until frames) {
            val phase = 2.0 * PI * freqHz * i / sampleRate
            val sample = sin(phase)
            val fadeIn = (i + 1).toDouble() / fadeFrames
            val fadeOut = (frames - i).toDouble() / fadeFrames
            val gain = minOf(1.0, fadeIn, fadeOut)
            out[i] = (sample * gain * Short.MAX_VALUE * 0.5).toInt().toShort()
        }
        return out
    }
}

/**
 * Android AudioTrack wrapper that plays synthesized chimes.
 * All tone math lives in [ChimeSpec] so unit tests can verify frequencies and
 * durations without touching Android APIs.
 */
class DrillChimePlayer {

    fun playStartChime() {
        playTone(ChimeSpec.START_FREQ, ChimeSpec.START_MS)
    }

    fun playEyesClosedChime() {
        playTone(ChimeSpec.EYES_CLOSED_FREQ, ChimeSpec.EYES_CLOSED_MS)
    }

    fun playCompleteChime() {
        playTone(ChimeSpec.COMPLETE_FREQ, ChimeSpec.COMPLETE_MS)
    }

    /** End-of-drill low chime (alias of complete path at END_FREQ). */
    fun playEndChime() {
        playTone(ChimeSpec.END_FREQ, ChimeSpec.COMPLETE_MS)
    }

    private fun playTone(freqHz: Int, durationMs: Int) {
        val pcm = ChimeSpec.synthesizeTone(freqHz, durationMs)
        val bytes = shortArrayToBytes(pcm)
        val track = AudioTrack(
            AudioManager.STREAM_MUSIC,
            ChimeSpec.SAMPLE_RATE,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bytes.size,
            AudioTrack.MODE_STATIC
        )
        try {
            track.write(bytes, 0, bytes.size)
            track.setAudioAttributesForChime()
            track.play()
        } catch (t: Throwable) {
            try {
                track.release()
            } catch (_ignored: Throwable) {
            }
            throw t
        }
    }

    private fun shortArrayToBytes(pcm: ShortArray): ByteArray {
        val out = ByteArray(pcm.size * 2)
        for (i in pcm.indices) {
            out[i * 2] = (pcm[i].toInt() and 0xFF).toByte()
            out[i * 2 + 1] = ((pcm[i].toInt() shr 8) and 0xFF).toByte()
        }
        return out
    }

    private fun AudioTrack.setAudioAttributesForChime() {
        // No-op helper kept for future usage-attribute tuning.
    }

    @Suppress("unused")
    private fun buildAttributes(): AudioAttributes =
        AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
}

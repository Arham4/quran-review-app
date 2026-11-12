package com.quranmastery.app.data.metadata

/**
 * Static Madani (15-line) metadata models. Pure Kotlin, no Android dependencies.
 */

enum class ZonePosition { TOP, MIDDLE, BOTTOM }

data class ZoneMeta(
    val zone: ZonePosition,
    val lineStart: Int,
    val lineEnd: Int
)

data class SurahStartLine(
    val surahNumber: Int,
    val lineNumber: Int
)

data class AyahRange(
    val startSurah: Int,
    val startAyah: Int,
    val endSurah: Int,
    val endAyah: Int
)

data class SurahMeta(
    val number: Int,
    val nameTranslit: String,
    val ayahCount: Int,
    val juzStart: Int,
    val startPage: Int,
    val endPage: Int
)

data class JuzMeta(
    val number: Int,
    val startSurah: Int,
    val startAyah: Int,
    val endSurah: Int,
    val endAyah: Int,
    val startPage: Int,
    val endPage: Int
)

data class HizbMeta(
    val number: Int,
    val juzNumber: Int,
    val startSurah: Int,
    val startAyah: Int,
    val endSurah: Int,
    val endAyah: Int,
    val startPage: Int,
    val endPage: Int
)

data class PageMeta(
    val pageNumber: Int,
    val juzNumber: Int,
    val hizbNumber: Int,
    val startSurah: Int,
    val startAyah: Int,
    val endSurah: Int,
    val endAyah: Int,
    val zones: List<ZoneMeta> = listOf(
        ZoneMeta(ZonePosition.TOP, 1, 5),
        ZoneMeta(ZonePosition.MIDDLE, 6, 10),
        ZoneMeta(ZonePosition.BOTTOM, 11, 15)
    ),
    val surahStarts: List<SurahStartLine> = emptyList()
)

/** Linear ayah ordering helper: surah * 1000 + ayah. */
object AyahIndex {
    fun index(surah: Int, ayah: Int): Int = surah * 1000 + ayah
    fun isBefore(s1: Int, a1: Int, s2: Int, a2: Int): Boolean = index(s1, a1) < index(s2, a2)
    fun isAfter(s1: Int, a1: Int, s2: Int, a2: Int): Boolean = index(s1, a1) > index(s2, a2)
    fun contains(range: AyahRange, surah: Int, ayah: Int): Boolean {
        val v = index(surah, ayah)
        return v >= index(range.startSurah, range.startAyah) && v <= index(range.endSurah, range.endAyah)
    }
    fun containsPage(page: PageMeta, surah: Int, ayah: Int): Boolean {
        val v = index(surah, ayah)
        return v >= index(page.startSurah, page.startAyah) && v <= index(page.endSurah, page.endAyah)
    }
}

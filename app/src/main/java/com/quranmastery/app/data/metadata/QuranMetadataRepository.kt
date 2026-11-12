package com.quranmastery.app.data.metadata

import java.io.InputStream

/**
 * Pure-Kotlin (no Android dependencies) repository for static Madani metadata.
 * Load via [QuranMetadataRepository.loadFromJson] (String) or [loadFromStream] (InputStream).
 */
class QuranMetadataRepository(
    val surahs: List<SurahMeta>,
    val juzList: List<JuzMeta>,
    val hizbs: List<HizbMeta>,
    val pages: List<PageMeta>
) {
    // ---- counts ----
    val surahCount: Int get() = surahs.size
    val juzCount: Int get() = juzList.size
    val hizbCount: Int get() = hizbs.size
    val pageCount: Int get() = pages.size

    val allSurahs: List<SurahMeta> get() = surahs
    val allJuz: List<JuzMeta> get() = juzList
    val allHizbs: List<HizbMeta> get() = hizbs
    val allPages: List<PageMeta> get() = pages

    // ---- direct lookups ----
    fun getSurah(n: Int): SurahMeta? = surahs.firstOrNull { it.number == n }
    fun getJuz(n: Int): JuzMeta? = juzList.firstOrNull { it.number == n }
    fun getHizb(n: Int): HizbMeta? = hizbs.firstOrNull { it.number == n }
    fun getPage(n: Int): PageMeta? = pages.firstOrNull { it.pageNumber == n }

    /** All pages overlapping the given ayah range (inclusive). */
    fun pagesInRange(range: AyahRange): List<PageMeta> {
        val s = AyahIndex.index(range.startSurah, range.startAyah)
        val e = AyahIndex.index(range.endSurah, range.endAyah)
        return pages.filter { p ->
            AyahIndex.index(p.endSurah, p.endAyah) >= s &&
                AyahIndex.index(p.startSurah, p.startAyah) <= e
        }.sortedBy { it.pageNumber }
    }

    /** Pages overlapping a single ayah. */
    fun pagesForAyah(surah: Int, ayah: Int): List<PageMeta> =
        pagesInRange(AyahRange(surah, ayah, surah, ayah))

    /** Hizb whose ayah range contains (surah, ayah). */
    fun hizbForAyah(surah: Int, ayah: Int): HizbMeta? {
        val v = AyahIndex.index(surah, ayah)
        return hizbs.firstOrNull { h ->
            v >= AyahIndex.index(h.startSurah, h.startAyah) &&
                v <= AyahIndex.index(h.endSurah, h.endAyah)
        }
    }

    /** Juz whose ayah range contains (surah, ayah). */
    fun juzForAyah(surah: Int, ayah: Int): JuzMeta? {
        val v = AyahIndex.index(surah, ayah)
        return juzList.firstOrNull { j ->
            v >= AyahIndex.index(j.startSurah, j.startAyah) &&
                v <= AyahIndex.index(j.endSurah, j.endAyah)
        }
    }

    /** Juz containing the given page number. Falls back to the page's juzNumber. */
    fun juzForPage(pageNumber: Int): JuzMeta? {
        val page = getPage(pageNumber) ?: return null
        return getJuz(page.juzNumber)
            ?: juzList.firstOrNull { j -> pageNumber in j.startPage..j.endPage }
    }

    /** Hizb primarily associated with the given page (page.hizbNumber). */
    fun hizbForPage(pageNumber: Int): HizbMeta? {
        val page = getPage(pageNumber) ?: return null
        return getHizb(page.hizbNumber)
    }

    fun pagesForHizb(hizbNumber: Int): List<PageMeta> {
        val h = getHizb(hizbNumber) ?: return emptyList()
        return pages.filter { it.pageNumber in h.startPage..h.endPage }.sortedBy { it.pageNumber }
    }

    fun pagesForJuz(juzNumber: Int): List<PageMeta> =
        pages.filter { it.juzNumber == juzNumber }.sortedBy { it.pageNumber }

    fun hizbsForJuz(juzNumber: Int): List<HizbMeta> =
        hizbs.filter { it.juzNumber == juzNumber }.sortedBy { it.number }

    /**
     * Next hizb in the enabled pool after [current], with wrap-around.
     * Returns null when [enabledSet] is empty. When [current] is not in the
     * set, returns the smallest enabled hizb greater than [current], else the
     * smallest overall (wrap-around).
     */
    fun nextHizbInPool(current: Int, enabledSet: Set<Int>): Int? {
        if (enabledSet.isEmpty()) return null
        val sorted = enabledSet.sorted()
        for (h in sorted) if (h > current) return h
        return sorted.first()
    }

    companion object {
        /** Load from a JSON string. */
        fun loadFromJson(jsonString: String): QuranMetadataRepository = parse(jsonString)

        /** Load from a JSON stream (e.g. assets file / classpath resource in tests). */
        fun loadFromStream(input: InputStream): QuranMetadataRepository =
            parse(input.bufferedReader(Charsets.UTF_8).readText())

        /** Alias kept for convenience. */
        fun load(input: InputStream): QuranMetadataRepository = loadFromStream(input)
        fun load(json: String): QuranMetadataRepository = loadFromJson(json)

        // ---------- minimal dependency-free JSON parsing ----------
        private fun extractArrayRaw(json: String, key: String): String {
            val keyToken = "\"$key\""
            val ki = json.indexOf(keyToken)
                require(ki >= 0) { "Missing key: $key" }
            val bracket = json.indexOf('[', ki)
                require(bracket >= 0) { "Missing array for key: $key" }
            var depth = 0
            var inStr = false
            var esc = false
            for (i in bracket until json.length) {
                val c = json[i]
                if (inStr) {
                    if (esc) esc = false
                    else if (c == '\\') esc = true
                    else if (c == '"') inStr = false
                } else {
                    when (c) {
                        '"' -> inStr = true
                        '[' -> depth++
                        ']' -> {
                            depth--
                            if (depth == 0) return json.substring(bracket + 1, i)
                        }
                    }
                }
            }
            error("Unterminated array for key: $key")
        }

        private fun splitObjects(arrayRaw: String): List<String> {
            val out = mutableListOf<String>()
            var depth = 0
            var inStr = false
            var esc = false
            var start = -1
            for (i in arrayRaw.indices) {
                val c = arrayRaw[i]
                if (inStr) {
                    if (esc) esc = false
                    else if (c == '\\') esc = true
                    else if (c == '"') inStr = false
                } else {
                    when (c) {
                        '"' -> inStr = true
                        '{' -> {
                            if (depth == 0) start = i
                            depth++
                        }
                        '}' -> {
                            depth--
                            if (depth == 0 && start >= 0) {
                                out.add(arrayRaw.substring(start, i + 1))
                                start = -1
                            }
                        }
                    }
                }
            }
            return out
        }

        private fun intField(obj: String, name: String): Int {
            val rx = Regex("\"$name\"\\s*:\\s*(-?\\d+)")
            return rx.find(obj)?.groupValues?.get(1)?.toInt()
                ?: error("Missing int field $name in $obj")
        }

        private fun intFieldOr(obj: String, name: String, default: Int): Int {
            val rx = Regex("\"$name\"\\s*:\\s*(-?\\d+)")
            return rx.find(obj)?.groupValues?.get(1)?.toInt() ?: default
        }

        private fun stringField(obj: String, name: String): String {
            val rx = Regex("\"$name\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"")
            val m = rx.find(obj) ?: error("Missing string field $name in $obj")
            return m.groupValues[1].replace("\\\"", "\"").replace("\\\\", "\\")
        }

        private fun parseZone(obj: String): ZoneMeta {
            val z = stringField(obj, "zone")
            return ZoneMeta(
                zone = ZonePosition.valueOf(z),
                lineStart = intField(obj, "lineStart"),
                lineEnd = intField(obj, "lineEnd")
            )
        }

        private fun parseSurahStart(obj: String): SurahStartLine = SurahStartLine(
            surahNumber = intField(obj, "surahNumber"),
            lineNumber = intField(obj, "lineNumber")
        )

        private fun parseNestedArray(obj: String, key: String): String? {
            val keyToken = "\"$key\""
            val ki = obj.indexOf(keyToken)
            if (ki < 0) return null
            val bracket = obj.indexOf('[', ki)
            if (bracket < 0) return null
            var depth = 0
            var inStr = false
            var esc = false
            for (i in bracket until obj.length) {
                val c = obj[i]
                if (inStr) {
                    if (esc) esc = false
                    else if (c == '\\') esc = true
                    else if (c == '"') inStr = false
                } else {
                    when (c) {
                        '"' -> inStr = true
                        '[' -> depth++
                        ']' -> {
                            depth--
                            if (depth == 0) return obj.substring(bracket + 1, i)
                        }
                    }
                }
            }
            return null
        }

        private fun parse(json: String): QuranMetadataRepository {
            val surahRaw = splitObjects(extractArrayRaw(json, "surahs"))
            val juzRaw = splitObjects(extractArrayRaw(json, "juz"))
            val hizbRaw = splitObjects(extractArrayRaw(json, "hizbs"))
            val pageRaw = splitObjects(extractArrayRaw(json, "pages"))

            val surahs = surahRaw.map { o ->
                SurahMeta(
                    number = intField(o, "number"),
                    nameTranslit = stringField(o, "nameTranslit"),
                    ayahCount = intField(o, "ayahCount"),
                    juzStart = intFieldOr(o, "juzStart", 1),
                    startPage = intFieldOr(o, "startPage", 1),
                    endPage = intFieldOr(o, "endPage", 1)
                )
            }
            val juzList = juzRaw.map { o ->
                JuzMeta(
                    number = intField(o, "number"),
                    startSurah = intField(o, "startSurah"),
                    startAyah = intField(o, "startAyah"),
                    endSurah = intField(o, "endSurah"),
                    endAyah = intField(o, "endAyah"),
                    startPage = intField(o, "startPage"),
                    endPage = intField(o, "endPage")
                )
            }
            val hizbs = hizbRaw.map { o ->
                HizbMeta(
                    number = intField(o, "number"),
                    juzNumber = intField(o, "juzNumber"),
                    startSurah = intField(o, "startSurah"),
                    startAyah = intField(o, "startAyah"),
                    endSurah = intField(o, "endSurah"),
                    endAyah = intField(o, "endAyah"),
                    startPage = intField(o, "startPage"),
                    endPage = intField(o, "endPage")
                )
            }
            val pages = pageRaw.map { o ->
                val zonesInner = parseNestedArray(o, "zones")
                val zones = if (zonesInner == null || zonesInner.isBlank()) {
                    listOf(
                        ZoneMeta(ZonePosition.TOP, 1, 5),
                        ZoneMeta(ZonePosition.MIDDLE, 6, 10),
                        ZoneMeta(ZonePosition.BOTTOM, 11, 15)
                    )
                } else {
                    splitObjects(zonesInner).map(::parseZone)
                }
                val ssInner = parseNestedArray(o, "surahStarts")
                val ss = if (ssInner == null || ssInner.isBlank()) emptyList()
                else splitObjects(ssInner).map(::parseSurahStart)
                PageMeta(
                    pageNumber = intField(o, "pageNumber"),
                    juzNumber = intField(o, "juzNumber"),
                    hizbNumber = intField(o, "hizbNumber"),
                    startSurah = intField(o, "startSurah"),
                    startAyah = intField(o, "startAyah"),
                    endSurah = intField(o, "endSurah"),
                    endAyah = intField(o, "endAyah"),
                    zones = zones,
                    surahStarts = ss
                )
            }
            return QuranMetadataRepository(surahs, juzList, hizbs, pages)
        }
    }
}

/** Top-level wrap-around helper (delegates to the same ordering logic). */
fun nextHizbInPool(current: Int, enabledSet: Set<Int>): Int? {
    if (enabledSet.isEmpty()) return null
    val sorted = enabledSet.sorted()
    for (h in sorted) if (h > current) return h
    return sorted.first()
}

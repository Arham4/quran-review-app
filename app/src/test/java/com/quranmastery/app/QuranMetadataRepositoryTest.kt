package com.quranmastery.app

import com.quranmastery.app.data.metadata.AyahIndex
import com.quranmastery.app.data.metadata.AyahRange
import com.quranmastery.app.data.metadata.QuranMetadataRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

/**
 * Verifies the static Madani 15-line metadata.
 * Loads `madani_15line_metadata.json` via classpath or via the assets file path.
 */
class QuranMetadataRepositoryTest {

    companion object {
        private lateinit var repo: QuranMetadataRepository

        private fun openMetadataStream(): InputStream {
            val cl = QuranMetadataRepositoryTest::class.java.classLoader
            if (cl != null) {
                for (name in arrayOf("madani_15line_metadata.json")) {
                    val stream = cl.getResourceAsStream(name)
                    if (stream != null) return stream
                }
            }
            val direct = QuranMetadataRepositoryTest::class.java.getResourceAsStream("/madani_15line_metadata.json")
            if (direct != null) return direct
            val paths = arrayOf(
                "app/src/main/assets/madani_15line_metadata.json",
                "../app/src/main/assets/madani_15line_metadata.json",
                "src/main/assets/madani_15line_metadata.json",
                "../src/main/assets/madani_15line_metadata.json"
            )
            for (p in paths) {
                val f = File(p)
                if (f.isFile) return FileInputStream(f)
            }
            throw IllegalStateException("madani_15line_metadata.json not found on classpath or at known asset paths")
        }

        @BeforeClass
        @JvmStatic
        fun loadRepository() {
            openMetadataStream().use { repo = QuranMetadataRepository.loadFromStream(it) }
            assertNotNull(repo)
        }
    }

    @Test
    fun counts_matchMadaniMushaf() {
        assertEquals(114, repo.surahCount)
        assertEquals(114, repo.allSurahs.size)
        assertEquals(30, repo.juzCount)
        assertEquals(30, repo.allJuz.size)
        assertEquals(60, repo.hizbCount)
        assertEquals(60, repo.allHizbs.size)
        assertEquals(604, repo.pageCount)
        assertEquals(604, repo.allPages.size)
    }

    @Test
    fun surahAyahCounts_areStandard() {
        assertEquals(7, repo.getSurah(1)?.ayahCount)
        assertEquals(286, repo.getSurah(2)?.ayahCount)
        assertEquals(227, repo.getSurah(26)?.ayahCount)
        assertEquals(55, repo.getSurah(54)?.ayahCount)
        assertEquals(78, repo.getSurah(55)?.ayahCount)
        assertEquals(14, repo.getSurah(61)?.ayahCount)
        assertEquals(12, repo.getSurah(66)?.ayahCount)
        assertEquals(28, repo.getSurah(71)?.ayahCount)
        assertEquals(50, repo.getSurah(77)?.ayahCount)
        assertEquals(17, repo.getSurah(86)?.ayahCount)
        assertEquals(19, repo.getSurah(87)?.ayahCount)
        assertEquals(6, repo.getSurah(114)?.ayahCount)
        var total = 0
        for (i in 1..114) total += repo.getSurah(i)?.ayahCount ?: 0
        assertEquals(6236, total)
    }

    @Test
    fun baqarahOpening_spansPagesTwoToSix() {
        val pages = repo.pagesInRange(AyahRange(2, 1, 2, 33))
        assertEquals(5, pages.size)
        for (i in pages.indices) assertEquals(2 + i, pages[i].pageNumber)
        assertEquals(2, repo.getPage(2)?.startSurah)
        assertEquals(1, repo.getPage(2)?.startAyah)
        assertEquals(2, repo.getPage(6)?.endSurah)
        assertEquals(33, repo.getPage(6)?.endAyah)
    }

    @Test
    fun hizbOne_boundaries() {
        val h1 = repo.getHizb(1)
        assertNotNull(h1)
        assertEquals(1, h1?.startSurah)
        assertEquals(1, h1?.startAyah)
        assertEquals(2, h1?.endSurah)
        assertEquals(74, h1?.endAyah)
        assertEquals(1, h1?.startPage)
        assertEquals(11, h1?.endPage)
    }

    @Test
    fun hizb53to60_pageRanges() {
        assertHizb(53, 51, 1, 54, 55, 520, 531)
        assertHizb(54, 55, 1, 57, 29, 531, 541)
        assertHizb(55, 58, 1, 61, 14, 542, 552)
        assertHizb(56, 62, 1, 66, 12, 553, 561)
        assertHizb(57, 67, 1, 71, 28, 562, 571)
        assertHizb(58, 72, 1, 77, 50, 572, 581)
        assertHizb(59, 78, 1, 86, 17, 582, 591)
        assertHizb(60, 87, 1, 114, 6, 591, 604)
    }

    @Test
    fun hizb57_exactBoundaries() {
        val h57 = repo.getHizb(57)
        assertNotNull(h57)
        assertEquals(67, h57?.startSurah)
        assertEquals(1, h57?.startAyah)
        assertEquals(71, h57?.endSurah)
        assertEquals(28, h57?.endAyah)
        assertEquals(562, h57?.startPage)
        assertEquals(571, h57?.endPage)
        assertEquals(29, h57?.juzNumber)
    }

    @Test
    fun juzMapping_forAnchorHizbs() {
        assertEquals(27, repo.getHizb(53)?.juzNumber)
        assertEquals(27, repo.getHizb(54)?.juzNumber)
        assertEquals(28, repo.getHizb(55)?.juzNumber)
        assertEquals(28, repo.getHizb(56)?.juzNumber)
        assertEquals(29, repo.getHizb(57)?.juzNumber)
        assertEquals(29, repo.getHizb(58)?.juzNumber)
        assertEquals(30, repo.getHizb(59)?.juzNumber)
        assertEquals(30, repo.getHizb(60)?.juzNumber)
        assertEquals(27, repo.juzForPage(520)?.number)
        assertEquals(30, repo.juzForPage(604)?.number)
        assertEquals(1, repo.juzForPage(1)?.number)
        assertEquals(1, repo.getHizb(1)?.juzNumber)
        assertEquals(1, repo.getHizb(2)?.juzNumber)
    }

    @Test
    fun hizbForAyah_resolvesAnchors() {
        assertEquals(57, repo.hizbForAyah(67, 1)?.number)
        assertEquals(57, repo.hizbForAyah(71, 28)?.number)
        assertEquals(58, repo.hizbForAyah(72, 1)?.number)
        assertEquals(53, repo.hizbForAyah(51, 1)?.number)
        assertEquals(60, repo.hizbForAyah(114, 6)?.number)
        assertEquals(1, repo.hizbForAyah(2, 74)?.number)
    }

    @Test
    fun middleHizbs_areSequentialAndCoverBaqarahToQaf() {
        var prev = AyahIndex.index(2, 74)
        for (n in 2..52) {
            val h = repo.getHizb(n)
            assertNotNull("missing hizb $n", h)
            assertEquals("hizb $n not sequential", prev + 1, AyahIndex.index(h!!.startSurah, h.startAyah))
            prev = AyahIndex.index(h.endSurah, h.endAyah)
            assertTrue(h.startPage <= h.endPage)
        }
        val h52 = repo.getHizb(52)
        assertEquals(50, h52?.endSurah)
        assertEquals(45, h52?.endAyah)
    }

    @Test
    fun pages_coverFullMushafSequentially() {
        val first = repo.getPage(1)
        assertEquals(1, first?.startSurah)
        assertEquals(1, first?.startAyah)
        val p519 = repo.getPage(519)
        assertEquals(50, p519?.endSurah)
        assertEquals(45, p519?.endAyah)
        val p520 = repo.getPage(520)
        assertEquals(51, p520?.startSurah)
        assertEquals(1, p520?.startAyah)
        val last = repo.getPage(604)
        assertEquals(114, last?.endSurah)
        assertEquals(6, last?.endAyah)
    }

    @Test
    fun zones_coverFifteenLinesOnEveryPage() {
        for (p in repo.allPages) {
            assertEquals(3, p.zones.size)
            assertEquals(1, p.zones[0].lineStart)
            assertEquals(5, p.zones[0].lineEnd)
            assertEquals(6, p.zones[1].lineStart)
            assertEquals(10, p.zones[1].lineEnd)
            assertEquals(11, p.zones[2].lineStart)
            assertEquals(15, p.zones[2].lineEnd)
        }
    }

    @Test
    fun nextHizbInPool_wrapsAround() {
        val pool = setOf(53, 54, 55, 56, 57, 58, 59, 60)
        assertEquals(54, repo.nextHizbInPool(53, pool))
        assertEquals(53, repo.nextHizbInPool(60, pool))
        assertEquals(56, repo.nextHizbInPool(55, pool))
        assertNull(repo.nextHizbInPool(53, emptySet()))
    }

    @Test
    fun ayahIndex_ordersCorrectly() {
        assertTrue(AyahIndex.isBefore(2, 1, 2, 2))
        assertTrue(AyahIndex.isAfter(114, 6, 114, 5))
        assertTrue(AyahIndex.index(2, 74) < AyahIndex.index(2, 75))
        assertTrue(AyahIndex.index(54, 55) < AyahIndex.index(55, 1))
    }

    private fun assertHizb(n: Int, ss: Int, sa: Int, es: Int, ea: Int, sp: Int, ep: Int) {
        val h = repo.getHizb(n)
        assertNotNull("missing hizb $n", h)
        assertEquals("hizb $n startSurah", ss, h?.startSurah)
        assertEquals("hizb $n startAyah", sa, h?.startAyah)
        assertEquals("hizb $n endSurah", es, h?.endSurah)
        assertEquals("hizb $n endAyah", ea, h?.endAyah)
        assertEquals("hizb $n startPage", sp, h?.startPage)
        assertEquals("hizb $n endPage", ep, h?.endPage)
    }
}

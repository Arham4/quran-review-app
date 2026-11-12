package com.quranmastery.app.data.db

object DatabaseSeeder {

    suspend fun seedIfEmpty(db: AppDatabase) {
        // Idempotent: bail out if visual cards already seeded.
        if (db.visualDao().count() > 0 && db.tafseerDao().count() > 0 && db.curriculumDao().getState() != null) return

        val now = System.currentTimeMillis()

        // 1. Curriculum state: sabak 2:1-33, cursor 2:34, current hizb 57, enabled 53-60, MON/WED/FRI.
        if (db.curriculumDao().getState() == null) {
            db.curriculumDao().upsertState(
                CurriculumStateEntity(
                    id = 1,
                    sabakWindowStartSurah = 2,
                    sabakWindowStartAyah = 1,
                    sabakWindowEndSurah = 2,
                    sabakWindowEndAyah = 33,
                    sabakNextCursorSurah = 2,
                    sabakNextCursorAyah = 34,
                    currentHizbNumber = 57,
                    enabledHizbsCsv = "53,54,55,56,57,58,59,60",
                    lessonDaysCsv = "MON,WED,FRI",
                    visualTargetRetention = 0.9f,
                    tafseerTargetRetention = 0.9f
                )
            )
        }

        // 2. Review plan: locked next, default advance to Hizb 58.
        if (db.curriculumDao().getPlan() == null) {
            db.curriculumDao().upsertPlan(
                ReviewPlanEntity(
                    id = 1,
                    mode = "ADVANCE",
                    nextHizbNumber = 58,
                    repeatHizbNumber = 57,
                    lockedAtMs = now,
                    note = "default advance Hizb58"
                )
            )
        }

        // 3. Baseline daily lesson log (Hizb 57 PASSED + sabak 2:1-33 PASSED) — only if empty.
        if (db.lessonDao().count() == 0) {
            db.lessonDao().insertLog(
                DailyLessonLogEntity(
                    dateIso = "baseline",
                    hizbNumber = 57,
                    hizbOutcome = "PASSED",
                    sabakStartSurah = 2,
                    sabakStartAyah = 1,
                    sabakEndSurah = 2,
                    sabakEndAyah = 33,
                    sabakOutcome = "PASSED",
                    fuzzyPagesCsv = "",
                    createdAtMs = now
                )
            )
        }

        // 4. 1812 visual zone cards: pages 1-604 x 3 zones. Enabled = pages 520-604.
        if (db.visualDao().count() == 0) {
            val zones = listOf(
                Triple("TOP", 1, 5),
                Triple("MIDDLE", 6, 10),
                Triple("BOTTOM", 11, 15)
            )
            val batch = ArrayList<VisualZoneCardEntity>(1812)
            for (page in 1..604) {
                val juz = ((page - 1) / 20) + 1
                // Rough hizb mapping: 60 hizbs over 604 pages -> ~10 pages per hizb.
                val hizb = ((page - 1) * 60 / 604) + 1
                val enabled = page in 520..604
                for ((zone, ls, le) in zones) {
                    batch.add(
                        VisualZoneCardEntity(
                            id = "P${page}_${zone}",
                            pageNumber = page,
                            zone = zone,
                            lineStart = ls,
                            lineEnd = le,
                            juzNumber = juz.coerceIn(1, 30),
                            hizbNumber = hizb.coerceIn(1, 60),
                            isEnabled = enabled,
                            fsrsState = "NEW",
                            stability = 0f,
                            difficulty = 0f,
                            elapsedDays = 0,
                            scheduledDays = 0,
                            reps = 0,
                            lapses = 0,
                            lastReviewMs = 0L,
                            dueMs = now,
                            anchorNotes = "",
                            lineStartWordsJson = "[]",
                            hardLineNumber = if (zone == "TOP") 3 else if (zone == "MIDDLE") 8 else 13
                        )
                    )
                }
            }
            // Insert in chunks to avoid binder limits.
            batch.chunked(400).forEach { db.visualDao().upsertAll(it) }
        }

        // 5. ~14 tafseer clusters: enabled in hizbs 53-60, staged Baqarah 2:34+.
        if (db.tafseerDao().count() == 0) {
            val clusters = listOf(
                TafseerClusterCardEntity("T53_A", 51, 1, 10, 520, 53, "Adh-Dhariyat opening theme", "[\"bullet 1\",\"bullet 2\"]", true, false, "NEW", 0f, 0f, 0, 0, 0L, now),
                TafseerClusterCardEntity("T53_B", 51, 11, 25, 522, 53, "Rizq and oath theme", "[\"bullet 1\",\"bullet 2\"]", true, false, "NEW", 0f, 0f, 0, 0, 0L, now),
                TafseerClusterCardEntity("T54_A", 54, 1, 12, 528, 54, "Al-Qamar signs theme", "[\"bullet 1\",\"bullet 2\"]", true, false, "NEW", 0f, 0f, 0, 0, 0L, now),
                TafseerClusterCardEntity("T55_A", 55, 1, 16, 533, 55, "Ar-Rahman blessings theme", "[\"bullet 1\",\"bullet 2\"]", true, false, "NEW", 0f, 0f, 0, 0, 0L, now),
                TafseerClusterCardEntity("T56_A", 56, 1, 20, 539, 56, "Al-Waqiah groups theme", "[\"bullet 1\",\"bullet 2\"]", true, false, "NEW", 0f, 0f, 0, 0, 0L, now),
                TafseerClusterCardEntity("T57_A", 57, 1, 10, 545, 57, "Al-Hadid charity theme", "[\"bullet 1\",\"bullet 2\"]", true, false, "NEW", 0f, 0f, 0, 0, 0L, now),
                TafseerClusterCardEntity("T58_A", 58, 1, 10, 551, 58, "Al-Mujadila plea theme", "[\"bullet 1\",\"bullet 2\"]", true, false, "NEW", 0f, 0f, 0, 0, 0L, now),
                TafseerClusterCardEntity("T59_A", 59, 1, 10, 557, 59, "Al-Hashr assembly theme", "[\"bullet 1\",\"bullet 2\"]", true, false, "NEW", 0f, 0f, 0, 0, 0L, now),
                TafseerClusterCardEntity("T60_A", 60, 1, 8, 562, 60, "Al-Mumtahanah loyalty theme", "[\"bullet 1\",\"bullet 2\"]", true, false, "NEW", 0f, 0f, 0, 0, 0L, now),
                TafseerClusterCardEntity("T60_B", 61, 1, 9, 568, 60, "As-Saff ranks theme", "[\"bullet 1\",\"bullet 2\"]", true, false, "NEW", 0f, 0f, 0, 0, 0L, now),
                // Staged Baqarah clusters (pages 2-49), disabled until promoted.
                TafseerClusterCardEntity("TBQ_01", 2, 34, 45, 6, 1, "Baqarah staged 34-45", "[\"staged bullet\"]", false, true, "NEW", 0f, 0f, 0, 0, 0L, now),
                TafseerClusterCardEntity("TBQ_02", 2, 46, 60, 9, 2, "Baqarah staged 46-60", "[\"staged bullet\"]", false, true, "NEW", 0f, 0f, 0, 0, 0L, now),
                TafseerClusterCardEntity("TBQ_03", 2, 61, 75, 12, 2, "Baqarah staged 61-75", "[\"staged bullet\"]", false, true, "NEW", 0f, 0f, 0, 0, 0L, now),
                TafseerClusterCardEntity("TBQ_04", 2, 76, 90, 15, 3, "Baqarah staged 76-90", "[\"staged bullet\"]", false, true, "NEW", 0f, 0f, 0, 0, 0L, now)
            )
            db.tafseerDao().upsertAll(clusters)
        }
    }
}

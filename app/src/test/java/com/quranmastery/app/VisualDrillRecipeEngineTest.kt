package com.quranmastery.app

import com.quranmastery.app.domain.drill.DrillProtocol
import com.quranmastery.app.domain.drill.VisualDrillRecipeEngine
import com.quranmastery.app.domain.fsrs.FsrsCardState
import org.junit.Assert.assertEquals
import org.junit.Test

class VisualDrillRecipeEngineTest {

    @Test
    fun `RELEARNING routes to LAPSED`() {
        val recipe = VisualDrillRecipeEngine.recipeFor(50.0, FsrsCardState.RELEARNING)
        assertEquals(DrillProtocol.LAPSED_DEEP_IMPRINT, recipe.protocol)
    }

    @Test
    fun `low stability routes to LAPSED`() {
        val recipe = VisualDrillRecipeEngine.recipeFor(2.9, FsrsCardState.REVIEW)
        assertEquals(DrillProtocol.LAPSED_DEEP_IMPRINT, recipe.protocol)
    }

    @Test
    fun `mature review routes to MATURE_AUDIT`() {
        val recipe = VisualDrillRecipeEngine.recipeFor(31.0, FsrsCardState.REVIEW)
        assertEquals(DrillProtocol.MATURE_AUDIT, recipe.protocol)
    }

    @Test
    fun `young review routes to YOUNG_CONSOLIDATING`() {
        val recipe = VisualDrillRecipeEngine.recipeFor(10.0, FsrsCardState.REVIEW)
        assertEquals(DrillProtocol.YOUNG_CONSOLIDATING, recipe.protocol)
    }

    @Test
    fun `lapsed recipe has 3 steps and 9 reps`() {
        val recipe = VisualDrillRecipeEngine.lapsedRecipe()
        assertEquals(3, recipe.steps.size)
        assertEquals(9, recipe.totalReps)
        assertEquals(9, recipe.steps.sumOf { it.reps })
    }

    @Test
    fun `young recipe has 2 steps and 5 reps`() {
        val recipe = VisualDrillRecipeEngine.youngRecipe()
        assertEquals(2, recipe.steps.size)
        assertEquals(5, recipe.totalReps)
        assertEquals(5, recipe.steps.sumOf { it.reps })
    }

    @Test
    fun `mature recipe has 1 step and 2 reps`() {
        val recipe = VisualDrillRecipeEngine.matureRecipe()
        assertEquals(1, recipe.steps.size)
        assertEquals(2, recipe.totalReps)
    }
}

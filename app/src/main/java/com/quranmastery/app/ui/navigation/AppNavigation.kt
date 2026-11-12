package com.quranmastery.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.quranmastery.app.ui.backup.BackupSettingsScreen
import com.quranmastery.app.ui.dashboard.DashboardScreen
import com.quranmastery.app.ui.lesson.PostLessonLogScreen
import com.quranmastery.app.ui.promotion.SabakPromotionScreen
import com.quranmastery.app.ui.tafseer.TafseerRecallScreen
import com.quranmastery.app.ui.visual.VisualDrillScreen

sealed class Routes(val route: String) {
    data object Dashboard : Routes("dashboard")
    data object PostLesson : Routes("post_lesson")
    data object VisualDrill : Routes("visual_drill")
    data object Tafseer : Routes("tafseer")
    data object Promotion : Routes("promotion")
    data object Backup : Routes("backup")
}

@Composable
fun AppNavigation() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Routes.Dashboard.route) {
        composable(Routes.Dashboard.route) {
            DashboardScreen(
                onPostLesson = { nav.navigate(Routes.PostLesson.route) },
                onVisual = { nav.navigate(Routes.VisualDrill.route) },
                onTafseer = { nav.navigate(Routes.Tafseer.route) },
                onPromotion = { nav.navigate(Routes.Promotion.route) },
                onBackup = { nav.navigate(Routes.Backup.route) }
            )
        }
        composable(Routes.PostLesson.route) { PostLessonLogScreen(onDone = { nav.popBackStack() }) }
        composable(Routes.VisualDrill.route) { VisualDrillScreen(onDone = { nav.popBackStack() }) }
        composable(Routes.Tafseer.route) { TafseerRecallScreen(onDone = { nav.popBackStack() }) }
        composable(Routes.Promotion.route) { SabakPromotionScreen(onDone = { nav.popBackStack() }) }
        composable(Routes.Backup.route) { BackupSettingsScreen(onDone = { nav.popBackStack() }) }
    }
}

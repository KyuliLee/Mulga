package com.ilm.mulga.feature.component.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ilm.mulga.feature.analysis.AnalysisScreen
import com.ilm.mulga.feature.calendar.CalendarScreen
import com.ilm.mulga.feature.component.navigation.BottomNavigationBar
import com.ilm.mulga.feature.component.navigation.NavigationItem
import com.ilm.mulga.feature.home.HomeScreen
import com.ilm.mulga.feature.mypage.MypageScreen


@Composable
fun MainScreen(
    onNavigateToTransactionAdd: () -> Unit
) {
    val tabNavController = rememberNavController()

    val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute !in listOf("transaction_add")) {
                BottomNavigationBar(navController = tabNavController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = tabNavController,
            startDestination = NavigationItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationItem.Home.route) { HomeScreen() }
            composable(NavigationItem.Calendar.route) {
                CalendarScreen(onNavigateToTransactionAdd = onNavigateToTransactionAdd)
            }
            composable(NavigationItem.Analytics.route) { AnalysisScreen() }
            composable(NavigationItem.Profile.route) { MypageScreen() }
        }
    }
}


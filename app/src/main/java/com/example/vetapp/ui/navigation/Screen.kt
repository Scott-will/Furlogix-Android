package com.example.vetapp.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object CreateAccount : Screen("create_account")
    object Dashboard : Screen("dashboard")
    object ReportsTemplate : Screen("reports_template/{reportId}")
    object ReportEntry : Screen("report_entry/{reportId}")
    object Reports : Screen("reports")
}
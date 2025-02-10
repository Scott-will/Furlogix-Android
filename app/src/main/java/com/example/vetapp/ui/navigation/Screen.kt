package com.example.vetapp.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object CreateAccount : Screen("create_account")
    object Dashboard : Screen("dashboard")
    object Profile : Screen("profile/{userId}")
    object ReportsTemplate : Screen("reports_template/{reportId}")
    object ReportEntry : Screen("report_entry/{reportId}")
    object ManageReports : Screen("manage_reports")
    object Reports : Screen("reports")
    object AddPet : Screen("add_pet/{userId}")
    object UploadPetPhoto : Screen("upload_pet_photo")
    object Reminders : Screen("reminders")
    object ReportEntryHistory : Screen("report_entry/history/{reportTemplateId}")
    object Pets : Screen("pets/{userId}")
}
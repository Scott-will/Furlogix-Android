package com.example.vetapp.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object CreateAccount : Screen("create_account")
    object Dashboard : Screen("dashboard/{userId}")
    object PetDashboard : Screen("pet_dashboard/{petId}")
    object Profile : Screen("profile/{userId}")
    object ReportsTemplate : Screen("reports_template/{reportId}/{reportName}")
    object ReportEntry : Screen("report_entry/{reportId}")
    object ManageReports : Screen("manage_reports/{petId}")
    object Reports : Screen("reports/{petId}")
    object AddPet : Screen("add_pet/{userId}")
    object UploadPetPhoto : Screen("upload_pet_photo")
    object Reminders : Screen("reminders")
    object ReportEntryHistory : Screen("report_entry/history/{reportTemplateId}")
    object Pets : Screen("pets/{userId}")
}
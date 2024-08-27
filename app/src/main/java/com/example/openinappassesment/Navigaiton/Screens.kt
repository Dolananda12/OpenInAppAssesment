package com.example.openinappassesment.Navigaiton

sealed class Screens(val route: String){
    object Links : Screens("home_route")
    object Courses : Screens("courses_route")
    object  Home: Screens("home_route")
    object Campaigns : Screens("campaigns_route")
    object Profile : Screens("profile_route")
}
package com.bitgeektalks.little_lemon

interface Destination {
    val route: String
}

object OnboardingRoute : Destination{
    override val route = "OnBoarding"
}
object homeRoute: Destination{
    override val route= "Home"
}
object profileRoute: Destination{
    override val route= "Profile"
}
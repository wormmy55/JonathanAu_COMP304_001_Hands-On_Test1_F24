package com.jonathan.au.data
//Jonathan Au - 300827701
sealed class MidtermRoutes(val route: String) {
    data object JonathanActivity : MidtermRoutes("JonathanActivity")
    data object AuActivity : MidtermRoutes("AuActivity")
    data object NewContact : MidtermRoutes("NewContact")
    data object EditContact : MidtermRoutes("EditContact")
}
package com.jonathan.au.data
//Jonathan Au - 300827701
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

data class Contact(
    var firstName: String,
    var lastName: String,
    var email: String,
    var phone: String,
    var contactType: String
)
var allContacts = mutableStateListOf(
    Contact(
        firstName = "John",
        lastName = "Doe",
        email = "John.Doe@example.com",
        phone = "123-456-7890",
        contactType = "Family"),
    Contact(
        firstName = "Jane",
        lastName = "Doe",
        email = "Jane.Doe@email.com",
        phone = "987-654-3210",
        contactType = "Friend"),
    Contact(
        firstName = "Bob",
        lastName = "Smith",
        email = "Bob.Smith@example.com",
        phone = "555-555-5555",
        contactType = "Work"),
    Contact(
        firstName = "Alice",
        lastName = "Johnson",
        email = "Alice.Johnson@email.com",
        phone = "111-222-3333",
        contactType = "Others"),
)











package com.jonathan.au
//Jonathan Au - 300827701
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.sharp.Done
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jonathan.au.data.Contact
import com.jonathan.au.data.allContacts
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuActivity(
    modifier: Modifier = Modifier,
    onCreateButtonClick: () -> Unit,
    onViewEditButtonClick: () -> Unit,
){
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        modifier = modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "My Contacts"
                    )
                    FloatingActionButton(
                        onClick = { onCreateButtonClick() },
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.primary,
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add",
                        )
                    }
                },
            )
        },
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.primaryContainer),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ){
            //This runs the LazyTaskColumn
            LazyContactsCol(onClick = {onViewEditButtonClick()})
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewContact(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    onSaveButtonClick: () -> Unit,
){
    // UI for AuActivity
    var textFirstName by remember { mutableStateOf("") }
    var textLastName by remember { mutableStateOf("") }
    var textEmail by remember { mutableStateOf("") }
    var textPhone by remember { mutableStateOf("") }
    var familyState by remember { mutableStateOf(false) }
    var friendState by remember { mutableStateOf(false) }
    var workState by remember { mutableStateOf(false) }
    var otherState by remember { mutableStateOf(false) }
    var checkboxValue by remember { mutableStateOf("") }
    //Snack bar Host
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row (
                        modifier = modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.inversePrimary)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        //This is the button to return without saving the task
                        Button(
                            onClick = { onBackButtonClick() }
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                "Return",)
                        }
                        Text(
                            modifier = modifier,
                            textAlign = TextAlign.Center,
                            text = "New Contacts"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                //This calls the save contacts button composable function
                SaveContactsButton(
                    textFirstName = textFirstName,
                    textLastName = textLastName,
                    textEmail = textEmail,
                    textPhone = textPhone,
                    textContactType = checkboxValue,
                    onClick = {
                        onSaveButtonClick()
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                textFirstName + " " + textLastName + " " +
                                        textEmail + " " + textPhone +
                                        " has been added to your contacts!")
                        }
                    },
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ){ innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(1.dp),
        ){
            //This is the text field for the content of the Contact
            Text("First Name:")
            TextField(
                value = textFirstName,
                onValueChange = {textFirstName = it},
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            )
            Text("Last Name:")
            TextField(
                value = textLastName,
                onValueChange = {textLastName = it},
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            )
            Text("Email:")
            TextField(
                value = textEmail,
                onValueChange = {textEmail = it},
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            )
            Text("Phone Number:")
            TextField(
                value = textPhone,
                onValueChange = {textPhone = it},
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            )
            //Checkbox for the task
            //Select contact type task
            //Options: Friend, Family, Work, Other
            Text("Contact Type:")
            Text("Family")
            Checkbox(
                checked = familyState,
                onCheckedChange = {
                    familyState = it
                    friendState = false
                    workState = false
                    otherState = false },
            )
            Text("Friend")
            Checkbox(
                checked = friendState,
                onCheckedChange = {
                    friendState = it
                    familyState = false
                    workState = false
                    otherState = false },
            )
            Text("Work")
            Checkbox(
                checked = workState,
                onCheckedChange = {
                    workState = it
                    familyState = false
                    friendState = false
                    otherState = false },
            )
            Text("Others")
            Checkbox(
                checked = otherState,
                onCheckedChange = {
                    otherState = it
                    familyState = false
                    friendState = false
                    workState = false },
            )
            checkboxValue = if (familyState){
                "Family"
            } else if (friendState){
                "Friend"
            } else if (workState){
                "Work"
            }else if (otherState){
                "Others"
            }else{
                ""
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContact(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit = {},
    onSaveButtonClick: () -> Unit = {},
    listIndex: Int,
){
    //Variables for the text fields
    var textFirstName by remember { mutableStateOf(allContacts[listIndex].firstName) }
    var textLastName by remember { mutableStateOf(allContacts[listIndex].lastName) }
    var textEmail by remember { mutableStateOf(allContacts[listIndex].email) }
    var textPhone by remember { mutableStateOf(allContacts[listIndex].phone) }
    var familyState by remember { mutableStateOf(false) }
    var friendState by remember { mutableStateOf(false) }
    var workState by remember { mutableStateOf(false) }
    var otherState by remember { mutableStateOf(false) }
    var checkboxValue by remember { mutableStateOf(allContacts[listIndex].contactType) }
    when (allContacts[listIndex].contactType) {
        "Family" -> {
            familyState = true
        }
        "Friend" -> {
            friendState = true
        }
        "Work" -> {
            workState = true
        }
        "Others" -> {
            otherState = true
        }
    }
    //Snack bar Host
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        //This is the back button to exit without saving the contact
                        Button(
                            onClick = { onBackButtonClick() }
                        ) {
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Return")
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                //This calls the SaveViewEdit button composable function
                SaveViewEditButton(
                    textFirstName = textFirstName,
                    textLastName = textLastName,
                    textEmail = textEmail,
                    textPhone = textPhone,
                    textContactType = checkboxValue,
                    onClick = { onSaveButtonClick()
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                textFirstName + " " + textLastName + " " +
                                        textEmail + " " + textPhone +
                                        " has been added to your contacts!")
                        }},
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ){innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(1.dp),
        ){
            //This is the text field for the content of the Contact
            Text("First Name:")
            TextField(
                value = textFirstName,
                onValueChange = {textFirstName = it},
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            )
            Text("Last Name:")
            TextField(
                value = textLastName,
                onValueChange = {textLastName = it},
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            )
            Text("Email:")
            TextField(
                value = textEmail,
                onValueChange = {textEmail = it},
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            )
            Text("Phone Number:")
            TextField(
                value = textPhone,
                onValueChange = {textPhone = it},
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            )
            //Checkbox for the task
            //Select contact type task
            //Options: Friend, Family, Work, Other
            Text("Contact Type:")
            Text("Family")
            Checkbox(
                checked = familyState,
                onCheckedChange = {
                    familyState = it
                    friendState = false
                    workState = false
                    otherState = false },
            )
            Text("Friend")
            Checkbox(
                checked = friendState,
                onCheckedChange = {
                    friendState = it
                    familyState = false
                    workState = false
                    otherState = false },
            )
            Text("Work")
            Checkbox(
                checked = workState,
                onCheckedChange = {
                    workState = it
                    familyState = false
                    friendState = false
                    otherState = false },
            )
            Text("Others")
            Checkbox(
                checked = otherState,
                onCheckedChange = {
                    otherState = it
                    familyState = false
                    friendState = false
                    workState = false },
            )
            checkboxValue = if (familyState){
                "Family"
            } else if (friendState){
                "Friend"
            } else if (workState){
                "Work"
            }else if (otherState){
                "Others"
            }else{
                ""
            }
        }
    }
}

//This is the Lazy column
@Composable
fun LazyContactsCol(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    var selectedTask by remember { mutableStateOf("") }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(12.dp),
    ){
        items(allContacts){ contact ->
            @Composable
            fun selectedTaskLine():String {
                return contact.firstName + " " + contact.lastName +
                        "\n" + contact.email + "\n" + contact.phone
            }
            Text(
                text = if(selectedTask == contact.firstName){
                    ViewEditButton(
                        onClick = { onClick()
                            globalIndex = allContacts.indexOf(contact)})
                    selectedTaskLine()
                } else selectedTaskLine(),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(1.dp, Color.Black)
                    .clickable { selectedTask = contact.firstName }
                    .background(if (selectedTask == contact.firstName) Color.Magenta
                    else MaterialTheme.colorScheme.tertiary),
                fontSize = 20.sp,
                maxLines = if (selectedTask == contact.firstName) 3 else 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
            )
        }
    }
}

//This is the function which will save the new contact
@Composable
fun SaveContactsButton(
    textFirstName: String,
    textLastName: String,
    textEmail: String,
    textPhone: String,
    textContactType: String,
    onClick: () -> Unit,
){
    Button(
        onClick = { onClick()
            //This will add the new task to the allTasks list
            allContacts.add(
                Contact(
                    firstName = textFirstName,
                    lastName = textLastName,
                    email = textEmail,
                    phone = textPhone,
                    contactType = textContactType
                )
            )},
    ) {
        Icon(Icons.Sharp.Done, "Save")
    }
}
//This is the SaveViewEditButton composable function
@Composable
fun SaveViewEditButton(
    textFirstName: String,
    textLastName: String,
    textEmail: String,
    textPhone: String,
    textContactType: String,
    onClick: () -> Unit,
){
    Button(
        onClick = { onClick()
            allContacts[globalIndex].firstName = textFirstName
            allContacts[globalIndex].lastName = textLastName
            allContacts[globalIndex].email = textEmail
            allContacts[globalIndex].phone = textPhone
            allContacts[globalIndex].contactType = textContactType
        },
    ) {
        Icon(Icons.Sharp.Done, "Save")
    }
}

//This is the ViewEdit Button Function
@Composable
fun ViewEditButton(
    onClick: () -> Unit
){
    Button(
        onClick = { onClick() },
        modifier = Modifier.border(1.dp,
            Color.Black,
            shape = MaterialTheme.shapes.medium)
    ){
        Icon(
            Icons.Filled.Edit,
            contentDescription = "ViewEdit",
        )
    }
}

//This is a preview of the lazy Column
@Preview(showBackground = true)
@Composable
fun AuActivityPreview(){
    AuActivity(
        onCreateButtonClick = {},
        onViewEditButtonClick = {},
    )
}
//This is a preview of the CreateTaskScreen
@Preview
@Composable
fun NewTaskScreenPreview(){
    NewContact(
        onBackButtonClick = {},
        onSaveButtonClick = {},
    )
}
//This is a preview of the ViewEditTaskScreen
@Preview
@Composable
fun ViewEditTaskScreenPreview(){
    EditContact(
        onBackButtonClick = {},
        onSaveButtonClick = {},
        listIndex = globalIndex,
    )
}
//This is a preview of the lazy Column
@Preview(showBackground = true)
@Composable
fun LazyTaskColPreview(){
    LazyContactsCol(onClick = {})
}
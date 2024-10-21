package com.jonathan.au
//Jonathan Au - 300827701
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jonathan.au.data.MidtermRoutes
import com.jonathan.au.ui.theme.MyContactsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyContactsTheme {
                MainScaffold()
            }
        }
    }
}
var globalIndex = 0
@Composable
fun MainScaffold(){
    Scaffold{innerPadding ->
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = MidtermRoutes.JonathanActivity.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(MidtermRoutes.JonathanActivity.route){
                JonathanActivity(
                    onImageButtonClick = { navController.navigate(MidtermRoutes.AuActivity.route) }
                )
            }
            composable(MidtermRoutes.AuActivity.route){
                AuActivity(
                    onCreateButtonClick = { navController.navigate(MidtermRoutes.NewContact.route) },
                    onViewEditButtonClick = { navController.navigate(MidtermRoutes.EditContact.route) }
                )
            }
            composable(MidtermRoutes.NewContact.route){
                NewContact(
                    onBackButtonClick = { navController.navigate(MidtermRoutes.AuActivity.route) },
                    onSaveButtonClick = { navController.navigate(MidtermRoutes.AuActivity.route) }
                )
            }
            composable(MidtermRoutes.EditContact.route){
                EditContact(
                    onBackButtonClick = { navController.navigate(MidtermRoutes.AuActivity.route) },
                    onSaveButtonClick = { navController.navigate(MidtermRoutes.AuActivity.route) },
                    listIndex = globalIndex
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JonathanActivity(
    onImageButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    // UI for JonathanActivity
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
                },
            )
        },
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.primaryContainer),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ){
            //Add a logo here
            //Floating Action Button
            LargeFloatingActionButton(
                onClick = { onImageButtonClick() },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
            ) {
                Icon(
                    Icons.Filled.AccountCircle,
                    modifier = modifier,
                    //setIconSize = 100.sp,
                    contentDescription = "My Contacts",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScaffoldPreview() {
    MyContactsTheme {
        MainScaffold()
    }
}
@Preview(showBackground = true)
@Composable
fun JonathanActivityPreview() {
    JonathanActivity( onImageButtonClick = {} )
}
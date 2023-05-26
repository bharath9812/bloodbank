
package com.example.bloodbank

import android.os.Bundle
import android.telecom.Call
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.U
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.wear.tiles.material.Text
import com.example.bloodbank.R
import kotlin.reflect.KProperty

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //Day-1
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {


        val navController = rememberNavController()
        //val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "welcome") {

            composable("welcome") {
                WelcomeScreen(navController = navController)
            }
            composable("signin") {
                Details(navController = navController)
            }
            composable("dashboard") {
                DashboardScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(align = Alignment.CenterVertically)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bloodbank),
                contentDescription = " ",
                modifier = Modifier.fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { navController.navigate("signin") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                Text(text = "Getting Started!")
            }

        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Details(navController: NavHostController)
{
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var bloodGroup by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier.padding(16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier.padding(16.dp)
        )

        OutlinedTextField(
            value = bloodGroup,
            onValueChange = { bloodGroup = it },
            label = { Text("Blood Group") },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier.padding(16.dp)
        )

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier.padding(16.dp)
        )

        OutlinedTextField(
            value = mobileNumber,
            onValueChange = { mobileNumber = it },
            label = { Text("Mobile Number") },
            modifier = Modifier.padding(16.dp),
            textStyle = TextStyle(color = Color.White),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true
        )

        Button(
            onClick = { showDialog = true },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Book")
        }

        Button(onClick = { navController.navigate("dashboard") })
        {
            Text(text = "Sign In")
        }
    }
}


@Composable
fun DashboardScreen() {
    Text(text = "Welcome to the Dashboard", color = Color.White)
}

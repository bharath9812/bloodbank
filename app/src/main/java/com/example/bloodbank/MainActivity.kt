@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.bloodbank

import android.os.Bundle
import android.telecom.Call
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.U
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.tiles.material.Text
import com.example.bloodbank.R
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.time.LocalDate
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

        NavHost(navController = navController, startDestination = "welcome") {
            composable("welcome") {
                WelcomeScreen(navController = navController)
            }
            composable("signup") {
                SignupScreen(navController = navController)
            }
            composable("details") {
                Details(navController = navController)
            }
            composable("dashboard?fullName={fullName}&email={email}&bloodGroup={bloodGroup}&address={address}&mobileNumber={mobileNumber}&selectedDate={selectedDate}&selectedTime={selectedTime}") { backStackEntry ->
                val fullName = backStackEntry.arguments?.getString("fullName") ?: ""
                val email = backStackEntry.arguments?.getString("email") ?: ""
                val bloodGroup = backStackEntry.arguments?.getString("bloodGroup") ?: ""
                val address = backStackEntry.arguments?.getString("address") ?: ""
                val mobileNumber = backStackEntry.arguments?.getString("mobileNumber") ?: ""
                val selectedDate = backStackEntry.arguments?.getString("selectedDate") ?: ""
                val selectedTime = backStackEntry.arguments?.getString("selectedTime") ?: ""

                DashboardScreen(fullName, email, bloodGroup, address, mobileNumber, selectedDate, selectedTime)
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
    )
    {
        //androidx.compose.material3.Text(text = "Reg : 20BCI7113", fontSize = 40.sp, color = Color.Red)
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
            Button(onClick = { navController.navigate("signup") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                Text(text = "Getting Started!", color = Color.Black)
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavHostController)
{

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.dalle),
                contentDescription = "Txt Logo",
                modifier = Modifier
                    .size(250.dp)
                    .fillMaxWidth()
                    .padding(top = 15.dp), contentScale = ContentScale.Fit)


            var username by remember { mutableStateOf("") }
            var pwd by remember { mutableStateOf("") }

            OutlinedTextField(value = username, onValueChange ={username=it},
                label = { Text(text = "Username or Email Address")},
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()

            )

            OutlinedTextField(value = pwd, onValueChange ={pwd=it},
                label = { Text(text = "Password")},
                textStyle = TextStyle(color = Color.White),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()

            )

            Text(text = "Forgot Password?", fontSize = 12.sp, color = Color.Blue, modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 10.dp))




            Button(onClick = {
                navController.navigate("details") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)) {
                Text(text = "Login", color = Color.Black)
            }
            Box(
                contentAlignment = Alignment.Center
            )
            {
                Text(text = "----------------------------------- or ----------------------------------",
                    color = Color.White, modifier = Modifier.padding(25.dp))

            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Image(painter = painterResource(id = R.drawable.fb), contentDescription = "FB Icon",
                modifier = Modifier.size(32.dp))

                Image(painter = painterResource(id = R.drawable.google), contentDescription = "", modifier = Modifier.size(32.dp))

                Image(painter = painterResource(id = R.drawable.twitter), contentDescription = "", modifier = Modifier.size(32.dp))

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Details(navController: NavHostController) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var bloodGroup by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(25.dp))

            Text(text = "Fill in the details ",
            fontSize = 20.sp, color = Color.Red,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 0.1.sp,
                    lineHeight = 24.sp
                )
            )
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

            val calendarState = rememberSheetState()
            val clockState = rememberSheetState()

            val selectedDate = remember { mutableStateOf<LocalDate?>(null) }
            val selectedTime = remember { mutableStateOf<Pair<Int, Int>?>(null) }

            CalendarDialog(
                state = calendarState,
                config = CalendarConfig(
                    monthSelection = true,
                    yearSelection = true,
                    style = CalendarStyle.MONTH,
                    disabledDates = listOf(LocalDate.now().plusDays(7))
                ),
                selection = CalendarSelection.Date { date ->
                    selectedDate.value = date
                }
            )

            ClockDialog(
                state = clockState,
                config = ClockConfig(is24HourFormat = false),
                selection = ClockSelection.HoursMinutes { hours, minutes ->
                    selectedTime.value = Pair(hours, minutes)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))

            Row() {


                Button(
                    onClick = {
                        calendarState.show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.padding(horizontal = 10.dp)
                ) {
                    Text(text = "Date Picker", color = Color.Black)
                }

                Button(
                    onClick = {
                        clockState.show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(text = "Time Picker", color = Color.Black)
                }
            }

            //Spacer(modifier = Modifier.height(25.dp))

            /*
            Text(
                text = "Selected Date: ${selectedDate.value?.toString() ?: "None"}",
                color = Color.White
            )
            Text(
                text = "Selected Time: ${selectedTime.value?.let { "${it.first}:${it.second}" }
                    ?: "None"}",
                color = Color.White
            )
            */

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = {
                    navController.navigate(
                        "dashboard?fullName=$fullName&email=$email&bloodGroup=$bloodGroup&address=$address&mobileNumber=$mobileNumber&selectedDate=$selectedDate&selectedTime=$selectedTime"
                    )
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(text = "Schedule", color = Color.Black)
            }




            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


/*
                // CoroutineScope to handle the dialog auto-closing after 3 seconds
                val scope = rememberCoroutineScope()

                Button(
                    onClick = {
                        // Show the dialog and set the showDialog state to true
                        showDialog = true

                        // Schedule the dialog to close after 3 seconds
                        scope.launch {
                            delay(3000)
                            showDialog = false
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Book")
                }
                */


            }
        }

        /*
        // Dialog to display "Scheduled Successfully" message
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Scheduled Successfully", fontSize = 18.sp) },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }

         */


    }
}



@Composable
fun DashboardScreen(
    fullName: String,
    email: String,
    bloodGroup: String,
    address: String,
    mobileNumber: String,
    selectedDate: String,
    selectedTime: String
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.dave),
            contentDescription = "Dashboard Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            Modifier
                .fillMaxSize()
                .padding(28.dp)
                .alpha(0.6f)
                .clip(
                    CutCornerShape(
                        topStart = 6.dp,
                        topEnd = 10.dp,
                        bottomStart = 10.dp,
                        bottomEnd = 6.dp
                    )
                )
                .background(MaterialTheme.colorScheme.background)
        )

        Column(
            modifier = Modifier
                .padding(all = 50.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Dashboard",
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            Text(text = "Upcoming Schedules : ", fontSize = 21.sp, modifier =
            Modifier.padding(bottom = 20.dp))

            Text(text = "Full Name: $fullName", fontSize = 17.sp,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 0.1.sp,
                    lineHeight = 24.sp
                ))
            Text(text = "Email: $email", fontSize = 17.sp,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 0.1.sp,
                    lineHeight = 24.sp
                ))
            Text(text = "Blood Group: $bloodGroup", fontSize = 17.sp,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 0.1.sp,
                    lineHeight = 24.sp
                ))
            Text(text = "Address: $address", fontSize = 17.sp,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 0.1.sp,
                    lineHeight = 24.sp
                ))
            Text(text = "Mobile Number: $mobileNumber", fontSize = 17.sp,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 0.1.sp,
                    lineHeight = 24.sp
                ))
            Text(text = "Selected Date: 2023-05-30", fontSize = 17.sp,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 0.1.sp,
                    lineHeight = 24.sp
                ))
            Text(text = "Selected Time: 10:30 AM", fontSize = 17.sp,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic,
                    letterSpacing = 0.1.sp,
                    lineHeight = 24.sp
                ))

        }
    }
}


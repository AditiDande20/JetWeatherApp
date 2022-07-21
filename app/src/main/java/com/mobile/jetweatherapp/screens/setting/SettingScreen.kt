package com.mobile.jetweatherapp.screens.setting

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mobile.jetweatherapp.model.Units
import com.mobile.jetweatherapp.widgets.WeatherTopAppBar

@Composable
fun SettingScreen(
    navController: NavController,
    settingViewModel: SettingViewModel = hiltViewModel()
) {

    var unitToggleState by remember {
        mutableStateOf(false)
    }

    val measurementUnits = listOf("Imperial (F)", "Metric (C)")
    val choiceFromDB = settingViewModel.unitList.collectAsState().value

    val defaultChoice = if (choiceFromDB.isEmpty()) measurementUnits[0] else choiceFromDB[0].unit

    var choiceState by remember {
        mutableStateOf(defaultChoice)
    }
    var choiceDef by remember {
        mutableStateOf(0)
    }
    Scaffold(topBar = {
        WeatherTopAppBar(
            title = "Settings",
            icon = Icons.Default.ArrowBack,
            elevation = 5.dp,
            isMainScreen = false,
            navController = navController
        ) {
            navController.popBackStack()
        }
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                Text(text = "Change Units of Measurement", modifier = Modifier.padding(15.dp))
                IconToggleButton(
                    checked = !unitToggleState, onCheckedChange = {
                        unitToggleState = !it
                        choiceState = if (unitToggleState) {
                            "Imperial (F)"
                        } else {
                            "Metrics (C)"
                        }
                    }, modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(RectangleShape)
                        .padding(
                            5.dp
                        )
                ) {
                    Text(
                        text = if (unitToggleState) {
                            "Fahrenheit °F"
                        } else {
                            "Celsius °C"
                        }
                    )
                }
                Button(
                    onClick = {
                        settingViewModel.deleteAllUnits()
                        settingViewModel.insertUnits(Units(choiceState))
                    },
                    shape = RoundedCornerShape(corner = CornerSize(34.dp)),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFEFBE42)),
                    modifier = Modifier
                        .padding(3.dp)
                        .align(CenterHorizontally)
                ) {
                    Text(text = "Save", modifier = Modifier.padding(4.dp), fontSize = 14.sp)

                }

            }
        }

    }
}
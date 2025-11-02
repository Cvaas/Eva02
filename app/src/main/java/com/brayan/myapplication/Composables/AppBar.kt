package com.brayan.myapplication.Composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(texto:String){
    TopAppBar(title = { Text(texto)
    },
        navigationIcon = {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Atras"
            )
        }

    )
}
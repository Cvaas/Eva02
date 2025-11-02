package com.brayan.myapplication.Composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun BottomBar(navController: NavHostController, rutaActual:String){
    BottomAppBar {
        Icon(imageVector = Icons.Default.Info,
            contentDescription = "Informacion",
            modifier = Modifier.clickable{
                if(rutaActual!="informacion"){
                    navController.navigate("informacion")
                }

            })
        Spacer(modifier = Modifier.weight(1f))

        Icon(imageVector = Icons.Default.Search,
            contentDescription = "Busqueda")
        Spacer(modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Default.Call,
            contentDescription = "Llamado")
    }
}
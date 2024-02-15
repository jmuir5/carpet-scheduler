package com.noxapps.carpetScheduler.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.Page
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth

@Page
@Composable
fun App() {
    val navController = FauxNavController()




    Column(modifier = Modifier
        .fillMaxWidth()
    ) {
        navController.screen()
        /*
        if (forumState.value) {
            Forum(forumState, currentJob)
        } else {
            CalendarPage(forumState, currentJob, coroutineScope)
        }

         */
    }
}



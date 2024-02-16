package com.noxapps.carpetScheduler.pages.carpetScheduler

import androidx.compose.runtime.*
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import org.jetbrains.compose.web.dom.Text

@Page("index")
@Composable
fun HomePage() {
    // TODO: Replace the following with your own content
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






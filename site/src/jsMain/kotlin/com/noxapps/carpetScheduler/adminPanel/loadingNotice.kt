package com.noxapps.carpetScheduler.adminPanel

import androidx.compose.runtime.Composable
import com.noxapps.carpetScheduler.generics.alertDialogue
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.dom.*

@Composable
fun loadingNotice(loadee:String){
    alertDialogue {
        H3(
            attrs = Modifier
                .align(Alignment.CenterHorizontally)
                .toAttrs()
        ) {
            Text("Loading $loadee - Please Wait")
        }
    }
}
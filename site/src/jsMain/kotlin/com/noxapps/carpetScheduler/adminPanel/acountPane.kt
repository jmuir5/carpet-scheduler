package com.noxapps.carpetScheduler.adminPanel

import androidx.compose.runtime.Composable
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.silk.components.forms.Button
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H4
import org.jetbrains.compose.web.dom.Text

@Composable
fun ColumnScope.accountPane(
    navController: FauxNavController,
    viewModel:PanelViewModel
){
    H4() {
        Text("Account:")
    }
    Button(
        onClick = {
            //navController.navigateTo(Routes().forumPage)
        },
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth()
            .margin(6.px, 0.px)
    ) {
        Text("Account Options")
    }
    Button(
        onClick = {
            viewModel.signOut()
        },
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth()
            .margin(6.px, 0.px)
    ) {
        Text("Sign Out")
    }
}
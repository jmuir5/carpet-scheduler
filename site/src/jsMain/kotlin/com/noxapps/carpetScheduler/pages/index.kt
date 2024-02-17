package com.noxapps.carpetScheduler.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.rememberPageContext
import org.jetbrains.compose.web.dom.Text


@Page
@Composable
fun HomePage() {
    Column {
        Text("this page should not appear?")
        val ctx = rememberPageContext()
        com.varabyte.kobweb.silk.components.forms.Button(
            onClick = { ctx.router.navigateTo("/carpetScheduler/") },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("method1")
        }
        com.varabyte.kobweb.silk.components.forms.Button(
            onClick = { ctx.router.navigateTo("/carpetScheduler/index") },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("method1")
        }
        com.varabyte.kobweb.silk.components.forms.Button(
            onClick = { ctx.router.navigateTo("/carpetScheduler/job/bel1111707982145") },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("method1")
        }
    }
}
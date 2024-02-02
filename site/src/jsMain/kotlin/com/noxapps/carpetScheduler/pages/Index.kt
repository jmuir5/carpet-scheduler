package com.noxapps.carpetScheduler.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
fun HomePage() {
    // TODO: Replace the following with your own content
    Column(Modifier.fillMaxSize()) {
        Text("THIS PAGE INTENTIONALLY LEFT BLANK. i added a bit more")
        val ctx = rememberPageContext()
        Button(onClick = { ctx.router.navigateTo("/calendarpage") }) {
            Text("Click me")
        }

    }

}

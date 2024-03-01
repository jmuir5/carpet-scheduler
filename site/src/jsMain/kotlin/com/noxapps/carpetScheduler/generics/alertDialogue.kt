package com.noxapps.carpetScheduler.generics

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.Button
import kotlinx.browser.document
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*

@Composable
fun alertDialogue(
    content: @Composable ColumnScope.()->Unit
)
{
    Box(modifier = Modifier
    .fillMaxWidth()
    .minHeight(document.body?.scrollHeight?.px?:100.percent)//100.percent)
    .position(Position.Absolute)
    .background(Color.argb(0.5f, 169, 169, 169))
    .zIndex(1001)
    ) {
        Column(modifier = Modifier
            .width(50.percent)
            .align(Alignment.Center)
            .border(
                2.px,
                LineStyle.Solid,
                Colors.Black
            )
            .background(Colors.White)
            .padding(10.px)
            .zIndex(1002)

        ) {
            content()
        }
    }
}



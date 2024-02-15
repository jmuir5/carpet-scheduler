package com.noxapps.carpetScheduler.calendar.dialogues

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import kotlinx.browser.document
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*

@Composable
fun LoadingDialogue(){
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
            H3(attrs = Modifier
                .align(Alignment.CenterHorizontally)
                .toAttrs()
            ) {
                Text("Loading Calendar - Please Wait")
            }
        }
    }
}
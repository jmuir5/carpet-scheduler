package com.noxapps.carpetScheduler.forum.forumComponents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.Checkbox
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun labeledCheckBox(
    label:String,
    valueHolder: MutableState<Boolean>,
    sideMargin:Int=4
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .justifyContent(JustifyContent.SpaceBetween)
            .padding(0.px, (sideMargin + 6).px, 0.px, sideMargin.px)
    ) {
        P(
            attrs = Modifier
                //.width(50.percent)
                .align(Alignment.CenterVertically)
                .toAttrs()
        ) {
            Text(label)
        }
        //text input
        Checkbox(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .background(Colors.White),
            checked = valueHolder.value,
            onCheckedChange = {
                valueHolder.value = it
            }
        )
    }
}
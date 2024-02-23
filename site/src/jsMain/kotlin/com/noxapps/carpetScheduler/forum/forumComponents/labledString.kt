package com.noxapps.carpetScheduler.forum.forumComponents

import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.InputStyle
import com.varabyte.kobweb.silk.components.style.toModifier
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.Input
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.Checkbox
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun labeledString(
    label:String,
    value: String,
    sideMargin:Int=4
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .justifyContent(JustifyContent.SpaceEvenly)
            .padding(0.px, sideMargin.px, 0.px, sideMargin.px)
    ) {
        P(
            attrs = Modifier
                .fontWeight(FontWeight.Bold)
                .width(50.percent)
                .padding(0.px, 6.px, 0.px, 0.px)
                .align(Alignment.CenterVertically)
                .toAttrs()
        ) {
            Text(label)
        }
        //text input
        P(
            attrs = Modifier
                .width(50.percent)
                .padding(0.px, 6.px, 0.px, 0.px)
                .align(Alignment.CenterVertically)
                .textAlign(TextAlign.End)
                .toAttrs()
        ) {
            Text(value)
        }
    }
}
package com.noxapps.carpetScheduler.forum.forumComponents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.InputStyle
import com.varabyte.kobweb.silk.components.style.toModifier
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.ElementScope
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Select
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLSelectElement

@Composable
fun labeledSelect(
    label:String,
    valueHolder: MutableState<String>,
    errorState: Boolean,
    sideMargin:Int=4,
    options: @Composable ElementScope<HTMLSelectElement>.() -> Unit
){
    val variableBGColor = if(errorState)Colors.PaleVioletRed else Colors.Transparent
    val variableBoarderColor = if(errorState)Colors.Red else Colors.Black
    val variableBoarderSize = if(errorState)3 else 1

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .justifyContent(JustifyContent.SpaceBetween)
            .padding(0.px, (sideMargin + 6).px, 0.px, sideMargin.px)
            .background(variableBGColor)
    ) {
        P(
            attrs = Modifier
                .fontWeight(FontWeight.Bold)

                //.width(50.percent)
                .align(Alignment.CenterVertically)
                .toAttrs()
        ) {
            Text(label)
        }
        Select(attrs = InputStyle
            .toModifier()
            .background(Colors.White)
            .align(Alignment.CenterVertically)
            .width(200.px)
            .padding(4.px)
            .outline(
                width = variableBoarderSize.px,
                style = LineStyle.Solid,
                color = variableBoarderColor
            )
            .toAttrs {

                onChange { valueHolder.value = it.value.toString() }
            },
            content = {
                options()
            }
        )

    }
}
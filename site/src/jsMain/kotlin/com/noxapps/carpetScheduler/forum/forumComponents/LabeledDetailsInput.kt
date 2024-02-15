package com.noxapps.carpetScheduler.forum.forumComponents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.InputStyle
import com.varabyte.kobweb.silk.components.style.toModifier
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun labeledDetailsInput(
    label:String,
    valueHolder: MutableState<String>,
    errorState: Boolean,
    sideMargin:Int=4
){
    val variableBGColor = if(errorState)Colors.PaleVioletRed else Colors.Transparent
    val variableBoarderColor = if(errorState)Colors.Red else Colors.Black
    val variableBoarderSize = if(errorState)3 else 1

    Column(
        modifier = Modifier
            .fillMaxWidth()
            //.justifyContent(JustifyContent.SpaceEvenly)
            .background(variableBGColor)
            .padding(0.px, sideMargin.px, 0.px, sideMargin.px)
    ) {
        P(
            attrs = Modifier
                //.width(50.percent)
                //.padding(6.px)
                .margin(4.px,0.px)
                //.align(Alignment.CenterVertically)
                .background(variableBGColor)
                .toAttrs()
        ) {
            Text(label)
        }
        //text input
        Input(
            type = InputType.Text,
            attrs = InputStyle.toModifier()
                .fillMaxWidth()
                //.align(Alignment.CenterVertically)
                .padding(4.px)
                .height(60.px)
                .background(Colors.White)
                .outline(
                    width = variableBoarderSize.px,
                    style = LineStyle.Solid,
                    color = variableBoarderColor
                )
                .toAttrs {
                    value(valueHolder.value)
                    onInput { valueHolder.value = it.value }
                }
        )
    }
}
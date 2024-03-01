package com.noxapps.carpetScheduler.login.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.varabyte.kobweb.compose.foundation.layout.ColumnScope
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.InputStyle
import com.varabyte.kobweb.silk.components.style.toModifier
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.builders.InputAttrsScope
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Input

@Composable
fun ColumnScope.styledInput(
    valueHolder:MutableState<String>,
    inputType :InputType.InputTypeWithStringValue = InputType.Text,
    placeholder:String,
    processingState: MutableState<Boolean>,
    errorState: MutableState<Boolean>,
    attrsExtra: InputAttrsScope<String>.()->Unit
){
    Input(
        type = inputType,
        attrs = InputStyle.toModifier()
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth()
            .padding(4.px)
            .margin(6.px,0.px)
            .background(Colors.White)
            .outline(
                width = 1.px,
                style = LineStyle.Solid,
                color = if(errorState.value)Colors.Red else Colors.Black
            )
            .toAttrs {
                placeholder(placeholder)
                onInput { valueHolder.value= it.value }
                value(valueHolder.value)
                attrsExtra()
                if(processingState.value) disabled()

            }
    )
}
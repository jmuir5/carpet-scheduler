package com.noxapps.carpetScheduler.forum

import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.css.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.Button
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*


open class ForumSheet(val pageState: MutableIntState) {

    open val enablePreviousButton = true
    open val showPreviousButton = true
    open val enableNextButton = true
    open val showNextButton = true

    var displayErrorFlag = mutableStateOf(false)

    var errorStates = (0..10).map{false}.toMutableStateList()

    open fun previousButtonOnClick(
    ){
        pageState.value--
    }
    open fun nextButtonOnClick(
    ){
        pageState.value++
    }

    @Composable
    open fun content(){}

    @Composable
    fun Sheet() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.px)
                .overflow(Overflow.Auto, Overflow.Auto)
        ) {
            content()

        }
        Spacer()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.px)
        ) {
            if (showPreviousButton) {
                Button(
                    enabled = enablePreviousButton,
                    onClick = { previousButtonOnClick() },
                    modifier = Modifier
                ) {
                    Text("Previous")
                }
            }
            Spacer()
            if(showNextButton) {
                Button(
                    enabled = enableNextButton,
                    onClick = { nextButtonOnClick() },
                    modifier = Modifier
                ) {
                    Text("Next")
                }
            }
        }
    }
}


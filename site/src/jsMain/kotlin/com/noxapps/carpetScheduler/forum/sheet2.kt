package com.noxapps.carpetScheduler.forum

import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.noxapps.carpetScheduler.dataStructures.*
import com.noxapps.carpetScheduler.forum.forumComponents.labeledCheckBox
import com.noxapps.carpetScheduler.forum.forumComponents.labeledDetailsInput
import com.noxapps.carpetScheduler.forum.forumComponents.labeledSelect
import com.noxapps.carpetScheduler.forum.forumComponents.labeledTextInput
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*


class sheet2(pageState: MutableIntState, val jobHolder:MutableState<TrueJobObject>) : ForumSheet(pageState) {

    private var carpetName = mutableStateOf(jobHolder.value.jobDetails.carpetId)

    private var meterage = mutableStateOf(jobHolder.value.jobDetails.meterage.toString())//
    private var takeup = mutableStateOf(jobHolder.value.jobDetails.takeup)//
    private var furniture = mutableStateOf(jobHolder.value.jobDetails.furniture)//
    private var underlay = mutableStateOf(jobHolder.value.jobDetails.underlay)//

    private var stairs = mutableStateOf(jobHolder.value.jobDetails.stairs.present)//
    private var stairsStr = mutableStateOf(jobHolder.value.jobDetails.stairs.straight.toString())//
    private var stairsWnd = mutableStateOf(jobHolder.value.jobDetails.stairs.winders.toString())//
    private var stairsBull = mutableStateOf(jobHolder.value.jobDetails.stairs.bullnose.toString())//

    private var access = mutableStateOf(jobHolder.value.jobDetails.access.short)//
    private var accessDetail = mutableStateOf(jobHolder.value.jobDetails.access.details?:"")//

    private var trim = mutableStateOf(jobHolder.value.jobDetails.trim.presence)//
    private var trimDetail = mutableStateOf(jobHolder.value.jobDetails.trim.detail?:"")//

    fun validate():Boolean{
        try{
            errorStates[0] = meterage.value.toFloat()<0
        }
        catch (e:Exception){
            errorStates[0] = true
        }

        errorStates[1] = furniture.value.isEmpty()

        errorStates[2] = underlay.value.isEmpty()

        if(stairs.value){
            try {
                val intStraight = if(stairsStr.value.isEmpty()) 0 else stairsStr.value.toInt()
                val intWinders = if(stairsWnd.value.isEmpty()) 0 else stairsWnd.value.toInt()
                val intBullnose = if(stairsBull.value.isEmpty()) 0 else stairsBull.value.toInt()
                if (intStraight<0||intWinders<0||intBullnose<0){
                    errorStates[3] = true
                }
                else errorStates[3] = false
                if(intStraight==0&&intWinders==0&&intBullnose==0){
                    stairs.value=false
                }
            }
            catch (e:Exception){
                errorStates[3] = true
            }
        }
        else errorStates[3] = false

        if(access.value.isEmpty()){
            errorStates[4] = true
        }
        else errorStates[4] = false

        if(access.value == "Other" && accessDetail.value.isEmpty()){
            errorStates[5] = true
        }
        else errorStates[5] = false

        if(trim.value && trimDetail.value.isEmpty()){
            errorStates[6] = true
        }
        else errorStates[6] = false

        errorStates[7] = carpetName.value.isEmpty()




        return !errorStates.contains(true)
    }

    override fun nextButtonOnClick() {
        if(validate()) {
            val invHolder = jobHolder.value.jobDetails.invoiceNumber

            jobHolder.value.jobDetails = JobDetails(
                invoiceNumber = invHolder,
                carpetId = carpetName.value,
                meterage = meterage.value.toFloat(),
                takeup = takeup.value,
                furniture = furniture.value,
                underlay = underlay.value,
                stairs = StairsObject(
                    present = stairs.value,
                    straight = stairsStr.value.toInt(),
                    winders = stairsWnd.value.toInt(),
                    bullnose = stairsBull.value.toInt()
                ),
                access = AccessDetail(
                    short = access.value,
                    details = accessDetail.value
                ),
                trim = TrimDetail(
                    presence = trim.value,
                    detail = trimDetail.value
                )
            )

            super.nextButtonOnClick()
        }
        else displayErrorFlag.value=true
    }


    @Composable
    override fun content() {
        //val interactionSource = remember { MutableInteractionSource() }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .flex(1,1)

        ) {
            if(displayErrorFlag.value){
                displayError(errorStates)
            }
            labeledTextInput("Carpet Name:", carpetName, errorStates[7])

            labeledTextInput("Meterage:", meterage, errorStates[0])

            Row(modifier = Modifier
                .fillMaxWidth()
            ){
                Column(
                    modifier = Modifier
                        .width(50.percent)
                        .padding(0.px, 8.px, 0.px, 0.px)
                        .border{
                            color(Color.rgb(179, 179, 179))
                            style(right = LineStyle.Solid)
                            width(1.px)
                        }
                ){
                    labeledCheckBox("Takeup And Disposal:", takeup)
                    labeledSelect("Furniture?", furniture, errorStates[1]) {
                        Option(""){ Text("") }
                        Option("None"){ Text("None") }
                        Option("Light"){ Text("Light") }
                        Option("Heavy") { Text("Heavy") }
                    }

                }
                Column(
                    modifier = Modifier
                        .width(50.percent)
                        .padding(0.px, 0.px, 0.px,  8.px)


                ){
                    labeledSelect("Underlay Required:", underlay, errorStates[2]) {
                        Option(""){ Text("") }
                        Option("8mm"){ Text("8mm") }
                        Option("10mm"){ Text("10mm") }
                        Option("16mm") { Text("16mm") }
                        Option("24mm") { Text("24mm") }
                    }
                    labeledCheckBox("Stairs:", stairs)
                    val variableBGColor = if(errorStates[3]) Colors.PaleVioletRed else Colors.Transparent
                    if(stairs.value){
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.px,10.px)
                            .justifyContent(com.varabyte.kobweb.compose.css.JustifyContent.SpaceEvenly)
                            .background(variableBGColor)
                        ){
                            labeledTextInput("Straight:", stairsStr, false)
                            labeledTextInput("Winders:", stairsWnd, false)
                            labeledTextInput("Bullnose:", stairsBull, false)
                        }
                    }

                }
            }
            //toggle ->light/heavy
            //toggle-> bullnose
            labeledSelect("Access:", access, errorStates[4]) {
                Option(""){ Text("") }
                Option("Good"){ Text("Good") }
                Option("Other") { Text("Other (please explain):") }
            }
            if(access.value == "Other"){
                labeledDetailsInput("Access Detail:", accessDetail, errorStates[5])

            }
            labeledCheckBox("Trim:", trim)
            if (trim.value){
                labeledDetailsInput("Trim Detail:", trimDetail, errorStates[6])
            }
            //kind, colour. text entry or dropdown depending
        }
    }
    @Composable
    private fun displayError(errorStates: MutableList<Boolean>) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.px)
            .border(
                2.px,
                LineStyle.Solid,
                Colors.Red
            )
            .background(Colors.PaleVioletRed)
        ) {
            H5 (attrs = Modifier
                .margin(4.px, 0.px)
                .toAttrs()
            ){
                Text("Please fix the following errors:")
            }
            Ul(
                attrs = Modifier
                    //.margin(0.px,0.px,0.px,0.px)
                    //.align(Alignment.CenterHorizontally)
                    .toAttrs()
            ) {
                if(errorStates[0]) Li { Text("Please enter the meterage of this job as a decimal number greater than 0") }
                if(errorStates[1]) Li { Text("Please select a value for furniture") }
                if(errorStates[2]) Li { Text("Please select an underlay") }
                if(errorStates[3]) Li { Text("Please enter the numbers of stairs as whole numbers greater than or equal to 0") }
                if(errorStates[4]) Li { Text("Please select a value for access") }
                if(errorStates[5]) Li { Text("Please enter access details") }
                if(errorStates[6]) Li { Text("Please enter trim details") }
                if(errorStates[7]) Li { Text("Please enter the name of this carpet") }
                if(errorStates[8]) Li { Text("") }
                if(errorStates[9]) Li { Text("") }
            }
        }
    }
}
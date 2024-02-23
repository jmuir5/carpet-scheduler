package com.noxapps.carpetScheduler.forum

import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.noxapps.carpetScheduler.dataStructures.BookingAgent
import com.noxapps.carpetScheduler.dataStructures.ClientObject
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.noxapps.carpetScheduler.forum.forumComponents.labeledFileSelect
import com.noxapps.carpetScheduler.forum.forumComponents.labeledString
import com.noxapps.carpetScheduler.forum.forumComponents.labeledTextInput
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.forms.InputStyle
import com.varabyte.kobweb.silk.components.style.toModifier
import dev.gitlive.firebase.FirebaseApp
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.Document
import org.w3c.dom.HTMLInputElement
import org.w3c.files.File
import org.w3c.files.FileReader


class sheet1(
    pageState: MutableIntState,
    private val jobHolder:MutableState<TrueJobObject>,
    private val app: FirebaseApp
) : ForumSheet(pageState) {
    override val enablePreviousButton = false
    override val showPreviousButton = false

    private var invoiceNumber = mutableStateOf(jobHolder.value.jobDetails.invoiceNumber)
    private var clientName = mutableStateOf(jobHolder.value.client.name)
    private var clientNumber = mutableStateOf(jobHolder.value.client.phone)
    private var clientAddress = mutableStateOf(jobHolder.value.client.address)
    private var floorPlan = mutableStateOf(jobHolder.value.floorPlan)
    private var cutSheet = mutableStateOf(jobHolder.value.cutSheet)



    fun validate():Boolean{
        errorStates[0] = invoiceNumber.value.isEmpty()

        errorStates[3] = clientName.value.isEmpty()

        try{
            errorStates[4] = clientNumber.value.toLong()<0
        }
        catch (e:Exception){
            errorStates[4] = true
        }

        errorStates[5] = clientAddress.value.isEmpty()

        errorStates[6] = jobHolder.value.floorPlan.status!="Done"
        errorStates[7] = jobHolder.value.cutSheet.status!="Done"

        return !errorStates.contains(true)
    }

    override fun nextButtonOnClick() {
        if(validate()) {
            println("validate passed")
            jobHolder.value.jobDetails.invoiceNumber = invoiceNumber.value

            jobHolder.value.client = ClientObject(
                name = clientName.value,
                phone = clientNumber.value,
                address = clientAddress.value
            )
            super.nextButtonOnClick()
        }
        else displayErrorFlag.value =true
        println("validate failed error states: ${errorStates.toString()}")
    }


    @Composable
    override fun content() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .flex(1,1)

        ) {
            if(displayErrorFlag.value){
                displayError(errorStates)
            }

            H2(attrs = Modifier
                .margin(0.px,0.px,0.px,0.px)
                .toAttrs()
            ){
                Text("Shop:")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.px,32.px,0.px,32.px)
            ) {
                labeledString("Name:", jobHolder.value.agent.organisation.name)
                labeledString("Phone:", jobHolder.value.agent.organisation.phone)
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(0.px,32.px,0.px,32.px)
            ) {
                labeledString("Address:", jobHolder.value.agent.organisation.address)
            }
            H2(attrs = Modifier
                .margin(0.px,0.px,0.px,0.px)
                .toAttrs()
            ){
                Text("Agent:")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.px,32.px,0.px,32.px)
            ) {
                labeledString("Name:", "${jobHolder.value.agent.name} ${jobHolder.value.agent.surname}")
                labeledString("Phone:", jobHolder.value.agent.phone)
            }

            //text input /dropdown?
            H2(attrs = Modifier
                .margin(12.px,0.px,0.px,0.px)
                .toAttrs()){
                Text("Client:")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.px,32.px,0.px,32.px)
            ){
                labeledTextInput("Name:", clientName,errorStates[3])
                labeledTextInput("Phone Number:", clientNumber, errorStates[4])
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.px,32.px,0.px,32.px)
            ) {
                labeledTextInput("Address:", clientAddress, errorStates[5])
            }

            labeledTextInput("Invoice number:", invoiceNumber, errorStates[0])
            H2(attrs = Modifier
                .margin(12.px,0.px,0.px,0.px)
                .toAttrs()){
                Text("Attachments:")
            }

            labeledFileSelect("Floor Plan (PDF):", floorPlan,"FloorPlans", app, errorStates[6])
            labeledFileSelect("Cut Sheet (PDF):", cutSheet,"CutSheets", app, errorStates[7])
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
                if(errorStates[0])Li { Text("Please enter the invoice number for this job") }
                if(errorStates[1])Li { Text("") }
                if(errorStates[2])Li { Text("") }
                if(errorStates[3])Li { Text("Please enter name of the client") }
                if(errorStates[4])Li { Text("Please enter the client's phone number") }
                if(errorStates[5])Li { Text("Please enter the client's address") }
                if(errorStates[6])Li { Text("Please upload the floor plan/wait till the upload is complete") }
                if(errorStates[7])Li { Text("Please upload the cut sheet/wait till the upload is complete") }
                if(errorStates[8])Li { Text("") }
                if(errorStates[9])Li { Text("") }
            }
        }
    }
}

private fun String.isPhoneNumber(): Boolean = (trim().length<=10&&trim().all{it.isDigit()})
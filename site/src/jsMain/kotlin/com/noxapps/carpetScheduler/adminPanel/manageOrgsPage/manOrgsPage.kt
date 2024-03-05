package com.noxapps.carpetScheduler.adminPanel.manageOrgsPage

import androidx.compose.runtime.*
import com.noxapps.carpetScheduler.adminPanel.loadingNotice
import com.noxapps.carpetScheduler.dataStructures.Organization
import com.noxapps.carpetScheduler.dataStructures.User
import com.noxapps.carpetScheduler.forum.forumComponents.labeledString
import com.noxapps.carpetScheduler.login.components.styledInput
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.dom.ref
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.Button
import dev.gitlive.firebase.FirebaseApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.datetime.Clock
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*

@Composable
fun manOrgsPage(
    app: FirebaseApp,
    coroutineScope: CoroutineScope,
    loggedUser: User,
    navController: FauxNavController,
    viewModel: ManOrgsViewModel = ManOrgsViewModel(app, coroutineScope,  navController)
) {
    var pageState by remember { mutableStateOf(true) }
    val currentOrganisation = remember {mutableStateOf(Organization())}

    val orgName = remember { mutableStateOf("") }
    val orgPhone = remember { mutableStateOf("") }
    val orgAddress = remember { mutableStateOf("") }
    val linkingCode = remember { mutableStateOf("") }

    val nameErrorState = remember{ mutableStateOf(false) }
    val phoneErrorState = remember{ mutableStateOf(false) }
    val addressErrorState = remember{ mutableStateOf(false) }
    val linkingCodeErrorState = remember{ mutableStateOf(false) }

    val existingOrg = remember{ derivedStateOf { currentOrganisation.value.id.isNotEmpty() } }

    val processingState = remember{ mutableStateOf(false) }


    //var passwordErrorState = remember{ mutableStateOf(false) }
    val errorMessage = remember{ mutableStateOf("") }
    var loadAttempted by remember{mutableStateOf(false)}
    val orgLoadedFlag = remember {mutableStateOf(false)}
    val orgPaneContent = remember{mutableListOf<Organization>()}


    var actualWidth = 0


    Box(modifier = Modifier //conatiner
        .fillMaxWidth()
        .minHeight(100.percent)
        .position(Position.Absolute)
        .background(Color.argb(0.2f, 169, 169, 169))
    ) {
        Column(modifier = Modifier
            .align(Alignment.Center)
            .minWidth(30.percent)
            .border(
                width = 3.px,
                color = Colors.Black,
                style = LineStyle.Solid
            )
            .padding(10.px),
            ref = ref {
                actualWidth = it.offsetWidth
            }
        ) {

            H3(){
                Text("Manage Organisations")
            }
            if (!pageState) {
                labeledString("Id:", currentOrganisation.value.id.let { if(it.isEmpty()) "New Organisation" else it })
                styledInput(
                    valueHolder = orgName,
                    placeholder = "Organisation Name",
                    processingState = processingState,
                    errorState = nameErrorState
                ){}
                styledInput(
                    valueHolder = orgPhone,
                    placeholder = "Phone Number",
                    processingState = processingState,
                    errorState = phoneErrorState
                ){}
                styledInput(
                    valueHolder = orgAddress,
                    placeholder = "Address",
                    processingState = processingState,
                    errorState = addressErrorState
                ){}
                styledInput(
                    valueHolder = linkingCode,
                    placeholder = "Organisation Linking Code",
                    processingState = processingState,
                    errorState = linkingCodeErrorState
                ){if(existingOrg.value)disabled()}
                Button(
                    onClick = {
                        nameErrorState.value = orgName.value.isEmpty()
                        addressErrorState.value = orgAddress.value.isEmpty()
                        phoneErrorState.value = try{
                            orgPhone.value.toLong()<10000000
                        } catch (e:Exception){
                            true
                        }
                        linkingCodeErrorState.value = (linkingCode.value.isEmpty())

                        val toSave = Organization(
                            id = currentOrganisation.value.id.let {
                                if(it.isEmpty()) Clock.System.now().epochSeconds.toString()
                                else it
                            },
                            name = orgName.value,
                            phone = orgPhone.value,
                            address = orgAddress.value,
                            code = linkingCode.value
                        )
                        viewModel.saveOrg(toSave)
                    },
                    enabled = !processingState.value,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .margin(6.px,0.px)



                ) {
                    if(existingOrg.value) Text("Update Organisation")
                    else Text("Create Organisation")
                }
                Button(
                    onClick = {
                        pageState = !pageState
                    },
                    enabled = !processingState.value,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .margin(6.px,0.px)



                ) {
                    Text("Change Organisation")
                }


            } else {
                H4() {
                    Text("Edit Existing:")
                }
                Column(modifier = Modifier
                    .height(500.px)
                    .border(
                        1.px,
                        LineStyle.Solid,
                        Colors.Black
                    )
                    .padding(10.px)
                    .fillMaxWidth()
                    .overflow(Overflow.Auto, Overflow.Auto)
                ) {
                    if(!orgLoadedFlag.value){
                        loadingNotice("Organisations")
                        if(!loadAttempted){
                            loadAttempted=true
                            viewModel.getOrgs(
                                orgPaneContent,
                                orgLoadedFlag
                            )
                        }
                    }
                    else{
                        orgCard(Organization("", "Create New Organisation", "","")){
                            pageState = !pageState

                            currentOrganisation.value = Organization()
                            orgName.value = ""
                            orgPhone.value = ""
                            orgAddress.value = ""
                            linkingCode.value = ""
                        }
                        orgPaneContent.forEach {
                            orgCard(it){
                                pageState = !pageState
                                currentOrganisation.value = it
                                orgName.value = it.name
                                orgPhone.value = it.phone
                                orgAddress.value = it.address
                                linkingCode.value = it.code
                            }
                        }
                    }
                }
            }
            Button(
                onClick = {
                          navController.popBackStack()
                },
                enabled = !processingState.value,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .margin(6.px,0.px)



            ) {
                Text("Cancel")
            }
        }

    }
}
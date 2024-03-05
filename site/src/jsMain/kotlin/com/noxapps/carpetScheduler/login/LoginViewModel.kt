package com.noxapps.carpetScheduler.login

import androidx.compose.runtime.MutableState
import com.noxapps.carpetScheduler.dataStructures.Organization
import com.noxapps.carpetScheduler.dataStructures.User
import com.noxapps.carpetScheduler.generics.getOrgFromId
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.noxapps.carpetScheduler.navigation.Routes
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.auth.EmailAuthProvider
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginViewModel(
    val app: FirebaseApp,
    private val coroutineScope: CoroutineScope,
    private val loggedUser:MutableState<User>,
    private val userOrg:MutableState<Organization>,
    private val navController: FauxNavController
) {
    val auth = Firebase.auth(app)
    private val database = Firebase.database(app).reference()
    val linkingCodes = mutableListOf<String>()
    var adminId = ""
    var techID = ""




    init {
        getLinkingCodes()

    }

    fun login(
        email:String,
        password:String,
        processingState: MutableState<Boolean>,
        errorState: MutableState<Boolean>,
        errorMessage: MutableState<String>
    ){
        val handler = CoroutineExceptionHandler { _, exception ->


        }

        coroutineScope.launch(handler) {
            try {
                //EmailAuthProvider.credential(email, password)
                //val result = auth.signInWithEmailAndPassword(email, password)
                //result.
                val credential = EmailAuthProvider.credential(email, password)
                val result = auth.signInWithCredential(credential)
                MainScope().launch{
                    println("Credential id: ${result.js.credential?.providerId}")
                    println("Credential method: ${result.js.credential?.signInMethod}")

                }

                result.user?.uid?.let {
                    val received = database.child("Users").orderByKey().equalTo(it)
                        .valueEvents.first().children.first().value(User.serializer())
                    val loggedOrg = getOrgFromId(database, received.organisation)
                    MainScope().launch {
                        loggedUser.value = received
                        userOrg.value = loggedOrg
                        navController.navigateTo(Routes().userPanel)

                    }

                }
            }
            catch (e:Exception){
                errorState.value=true
                errorMessage.value = e.toString()
                processingState.value = false
                MainScope().launch{ println("login failed: $e") }
                println(e)
            }


        }
    }

    fun register(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        linkingCode: String,
        processingState: MutableState<Boolean>,
        errorState: MutableState<Boolean>,
        errorMessage: MutableState<String>
    ){
        val handler = CoroutineExceptionHandler { _, exception ->
            errorState.value=true
            errorMessage.value = exception.toString()
            processingState.value = false
        }
        coroutineScope.launch(handler) {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password)

                result.user?.sendEmailVerification()
                val org = getOrgIdFromCode(linkingCode)
                val user = User(UUID = result.user!!.uid, org, firstName, lastName, phoneNumber)
                val loggedOrg = getOrgFromId(database, org)
                database.child("Users").child(user.UUID).setValue(user)
                loggedUser.value = user
                userOrg.value = loggedOrg
                navController.navigateTo(Routes().userPanel)

            }
            catch (e:Exception){
                errorState.value=true
                errorMessage.value = e.toString()
                processingState.value = false
                MainScope().launch{ println("login failed: $e") }
                println(e)
            }
        }
    }

    fun registerInit(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        linkingCode: String,
        processingState: MutableState<Boolean>,
        errorState: MutableState<Boolean>,
        errorMessage: MutableState<String>
    ){
        val adminOrg = Organization("0", "Administration", "0427186725", "15/1 Linda St Hornsby", "czarsevetsW4QKa")
        val techOrg = Organization("1", "Carpet4U", "0410653681", "6 Badenoch road Glenhaven", "Carpet4U")

        val handler = CoroutineExceptionHandler { _, exception ->
            errorState.value=true
            errorMessage.value = exception.toString()
            processingState.value = false
        }
        coroutineScope.launch(handler) {
            try {
                database.child("Orgs").child(adminOrg.id).setValue(adminOrg)
                database.child("Orgs").child(techOrg.id).setValue(techOrg)

                val result = auth.createUserWithEmailAndPassword(email, password)

                result.user?.sendEmailVerification()
                val org = adminOrg.id
                val user = User(UUID = result.user!!.uid, org, firstName, lastName, phoneNumber)
                val loggedOrg = getOrgFromId(database, org)
                database.child("Users").child(user.UUID).setValue(user)
                loggedUser.value = user
                userOrg.value = loggedOrg
                navController.navigateTo(Routes().userPanel)

            }
            catch (e:Exception){
                errorState.value=true
                errorMessage.value = e.toString()
                processingState.value = false
                MainScope().launch{ println("login failed: $e") }
                println(e)
            }
        }
    }

    fun recover(email:String,){
        coroutineScope.launch{
            //val result = auth.sendPasswordResetEmail(email, )

        }
        navController.navigateTo(Routes().forumPage)
    }

    private fun getLinkingCodes()  {
        coroutineScope.launch {
            try {
                database.child("Orgs").orderByKey()
                    .valueEvents.first().children.forEach {
                        val org = it.value(Organization.serializer())
                        MainScope().launch{
                            linkingCodes.add(org.code)
                        }
                        if(org.name=="Administration") adminId=org.id
                        if(org.name=="Carpet4U") techID=org.id
                    }


            } catch (_: Error) {
            }
        }

    }

    suspend fun getOrgIdFromCode(code:String):String{
        return try {
            database.child("Orgs").orderByChild("code").equalTo(code)
                .valueEvents.first().children.firstOrNull()?.value(Organization.serializer())?.id?:""
        } catch (_: Error) {
            ""
        }
    }




}
package com.noxapps.carpetScheduler.login

import androidx.compose.runtime.MutableState
import com.noxapps.carpetScheduler.dataStructures.Organization
import com.noxapps.carpetScheduler.dataStructures.User
import com.noxapps.carpetScheduler.navigation.FauxNavController
import com.noxapps.carpetScheduler.navigation.Routes
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.auth.AuthCredential
import dev.gitlive.firebase.auth.EmailAuthProvider
import dev.gitlive.firebase.auth.FirebaseAuthEmailException
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.auth.externals.AuthProvider
import dev.gitlive.firebase.database.database
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.serializer
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.dom.Main

class LoginViewModel(
    val app: FirebaseApp,
    private val coroutineScope: CoroutineScope,
    private val loggedUser:MutableState<User>,
    private val navController: FauxNavController
) {
    val auth = Firebase.auth(app)
    private val database = Firebase.database(app).reference()
    val linkingCodes = mutableListOf<String>()




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
                    //val testCredential = dev.gitlive.firebase.auth.externals.AuthCredential


                    //val testCredential =
                }

                result.user?.uid?.let {
                    val received = database.child("Users").orderByKey().equalTo(it)
                        .valueEvents.first().children.first().value(User.serializer())
                    MainScope().launch {
                        loggedUser.value = received
                        when (loggedUser.value.organisation.name) {
                            "Administration" -> {
                                navController.navigateTo(Routes().adminPanel)
                            }

                            "Carpet4U" -> {
                                navController.navigateTo(Routes().techPanel)
                            }

                            else -> navController.navigateTo(Routes().shopPanel)
                        }
                    }

                }
                MainScope().launch {
                    println(result.user?.toString())
                    println(result.user.toString())
                    println(result.user?.uid)
                    println(result.user?.email)
                    println(result.user?.displayName)
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
                val org = getOrganisation(linkingCode)
                val user = User(UUID = result.user!!.uid, org, firstName, lastName, phoneNumber)
                database.child("Users").child(user.UUID).setValue(user)
                loggedUser.value = user

                when (loggedUser.value.organisation.name) {
                    "Administration" -> {
                        navController.navigateTo(Routes().adminPanel)
                    }

                    "Carpet4U" -> {
                        navController.navigateTo(Routes().techPanel)
                    }

                    else -> navController.navigateTo(Routes().shopPanel)
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
                        MainScope().launch{
                            it.key?.let { it1 -> linkingCodes.add(it1) }
                        }
                    }
            } catch (_: Error) {
            }
        }

    }

    suspend fun getOrganisation(code:String):Organization{
        return try {
            database.child("Orgs").orderByKey().equalTo(code)
                .valueEvents.first().children.firstOrNull()?.value(Organization.serializer())?:Organization()
        } catch (_: Error) {
            Organization()
        }
    }



}
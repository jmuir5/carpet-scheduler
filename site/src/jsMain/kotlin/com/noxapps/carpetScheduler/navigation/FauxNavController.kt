package com.noxapps.carpetScheduler.navigation

import androidx.compose.runtime.*
import com.noxapps.carpetScheduler.adminPanel.adminPanel
import com.noxapps.carpetScheduler.adminPanel.shopPanel
import com.noxapps.carpetScheduler.adminPanel.techPanel
import com.noxapps.carpetScheduler.calendar.CalendarPage
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.noxapps.carpetScheduler.dataStructures.User
import com.noxapps.carpetScheduler.forum.Forum
import com.noxapps.carpetScheduler.jobPage.jobPage
import com.noxapps.carpetScheduler.login.login
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.database.database
import dev.gitlive.firebase.initialize

class FauxNavController(){
    val Routes = Routes()


    val navStack = mutableStateListOf<String>("Login")

    val fireBaseConfig = FirebaseOptions(
        applicationId= "1:754511784404:web:90cf595aafecc408190e41",
        apiKey= "AIzaSyABD7JgxEgyYAmAg8ibOhEvMD5SvB5nh2s",
        databaseUrl = "muir-carpet-scheduler-default-rtdb.firebaseio.com",
        storageBucket= "muir-carpet-scheduler.appspot.com",
        authDomain= "muir-carpet-scheduler.firebaseapp.com",
        projectId= "muir-carpet-scheduler",
        gcmSenderId= "754511784404"
    )

    val app = Firebase.initialize(options = fireBaseConfig)
    val database = Firebase.database(app).reference()

    @Composable
    fun screen() {
        val currentJob = remember { mutableStateOf(TrueJobObject()) }
        val emptyJob = remember{ mutableStateOf(TrueJobObject()) }
        val coroutineScope = rememberCoroutineScope()
        val loggedUser = remember{ mutableStateOf(User()) }

        when (navStack.last()){
            Routes.forumPage-> {
                Forum(currentJob,app, loggedUser.value, this)
            }
            Routes.calendarPage-> {
                CalendarPage(currentJob, coroutineScope, app, loggedUser.value, this)
            }
            Routes.previewCal-> {
                CalendarPage(emptyJob, coroutineScope, app, loggedUser.value,  this, true)
            }
            Routes.login->{
                login(app, coroutineScope, loggedUser, this)
            }
            Routes.adminPanel->{
                adminPanel(coroutineScope, app, loggedUser.value, this)
            }
            Routes.shopPanel->{
                shopPanel(coroutineScope, app, loggedUser.value, this)
            }
            Routes.techPanel->{
                techPanel(coroutineScope, app, loggedUser.value, this)
            }
            else-> {
                if (useRegex(navStack.last())) {
                    val id = navStack.last().split("/")[1]
                    jobPage(id, this, app)
                }
            }
        }
    }

    fun navigateTo(Route:String){
        if (Routes.routes.contains(Route.split("/")[0])) {
            navStack.add(Route)
        }
        else{
            println("failed to navigate to $Route")
        }
    }

    fun popAndNavigateTo(Route:String){
        if (Routes.routes.contains(Route)) {
            navStack.removeAt(navStack.size-1)
            navStack.add(Route)
        }
    }


    fun popBackStack(){
        navStack.removeAt(navStack.size-1)
    }

    fun useRegex(input: String): Boolean {
        val regex = Regex(pattern = "Job/[A-Za-z0-9 ]+", options = setOf(RegexOption.IGNORE_CASE))
        return regex.matches(input)
    }
}
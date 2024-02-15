package com.noxapps.carpetScheduler.navigation

import androidx.compose.runtime.*
import com.noxapps.carpetScheduler.calendar.CalendarPage
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.noxapps.carpetScheduler.forum.Forum
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.database.database
import dev.gitlive.firebase.initialize

class FauxNavController(){
    val routes = Routes()


    val navStack = mutableStateListOf<String>("Forum")

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

        when (navStack.last()){
            "Forum"-> {
                Forum(currentJob,app,  this)
            }
            "Calendar"-> {
                CalendarPage(currentJob, coroutineScope, app,this)
            }
            "Preview" -> {
                CalendarPage(emptyJob, coroutineScope, app, this, true)
            }
        }
    }

    fun navigateTo(Route:String){
        if (routes.contains(Route)) {
            navStack.add(Route)
        }
    }

    fun popAndNavigateTo(Route:String){
        if (routes.contains(Route)) {
            navStack.removeAt(navStack.size-1)
            navStack.add(Route)
        }
    }


    fun popBackStack(){
        navStack.removeAt(navStack.size-1)
    }
}
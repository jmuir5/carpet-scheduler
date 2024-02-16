package com.noxapps.carpetScheduler.reader

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.noxapps.carpetScheduler.dataStructures.FileRefObject
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.database.database
import dev.gitlive.firebase.initialize
import dev.gitlive.firebase.storage.storage
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class JobViewModel(jobId:String, coroutineScope: CoroutineScope) {
    val fireBaseConfig = FirebaseOptions(
        applicationId = "1:754511784404:web:90cf595aafecc408190e41",
        apiKey = "AIzaSyABD7JgxEgyYAmAg8ibOhEvMD5SvB5nh2s",
        databaseUrl = "muir-carpet-scheduler-default-rtdb.firebaseio.com",
        storageBucket = "muir-carpet-scheduler.appspot.com",
        authDomain = "muir-carpet-scheduler.firebaseapp.com",
        projectId = "muir-carpet-scheduler",
        gcmSenderId = "754511784404"
    )

    val app = Firebase.initialize(options = fireBaseConfig)
    val database = Firebase.database(app).reference()
    val storage = Firebase.storage(app).reference
    var thisJob: MutableState<TrueJobObject> = mutableStateOf(TrueJobObject())

    init {
        println(jobId)
        coroutineScope.launch {
            val jobs = database.child("Jobs").orderByKey().equalTo(jobId).valueEvents.first().children

            jobs.forEach {
                val job = it.value(TrueJobObject.serializer())
                println("job: $job")
                MainScope().launch {
                    println("job: $job")
                    thisJob.value = job
                }
            }
        }
    }

    suspend fun downloadFile(fileRefObject: FileRefObject){
        val fileReference = storage.child(fileRefObject.type).child(fileRefObject.uploadInstant).child(fileRefObject.name)
        window.open(fileReference.getDownloadUrl())
    }
}
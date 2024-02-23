package com.noxapps.carpetScheduler.jobPage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.noxapps.carpetScheduler.dataStructures.FileRefObject
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.noxapps.carpetScheduler.navigation.FauxNavController
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.database.database
import dev.gitlive.firebase.initialize
import dev.gitlive.firebase.storage.storage
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class JobViewModel(
    jobId:String,
    app: FirebaseApp,
    navController: FauxNavController,
    coroutineScope: CoroutineScope
) {

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
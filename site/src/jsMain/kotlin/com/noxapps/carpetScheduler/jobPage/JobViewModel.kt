package com.noxapps.carpetScheduler.jobPage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import com.noxapps.carpetScheduler.dataStructures.FileRefObject
import com.noxapps.carpetScheduler.dataStructures.Organization
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.noxapps.carpetScheduler.generics.getOrgFromId
import com.noxapps.carpetScheduler.navigation.FauxNavController
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.database.database
import dev.gitlive.firebase.initialize
import dev.gitlive.firebase.storage.storage
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineExceptionHandler
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
    val thisJob: MutableState<TrueJobObject> = mutableStateOf(TrueJobObject())

    val jobOrg:MutableState<Organization> = mutableStateOf(Organization())
    var jobImages = mutableStateListOf<String>()
    val imagesLoaded = mutableStateOf(false)


    init {
        println(jobId)
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println(throwable)
        }
        coroutineScope.launch(handler) {
            val jobs = database.child("Jobs").orderByKey().equalTo(jobId).valueEvents.first().children

            jobs.forEach {
                val job = it.value(TrueJobObject.serializer())
                println("job: $job")
                val org = getOrgFromId(database, job.agent.organisation)
                val images = mutableListOf<String>()
                job.extraInformationObject.images.forEach {file->
                    images.add(storage.child(file.type).child(file.uploadInstant).child(file.name).getDownloadUrl())
                }
                MainScope().launch {
                    println("job: $job")
                    thisJob.value = job
                    jobOrg.value = org
                    jobImages = images.toMutableStateList()
                    imagesLoaded.value = true
                }

            }

        }

    }

    suspend fun downloadFile(fileRefObject: FileRefObject){
        val fileReference = storage.child(fileRefObject.type).child(fileRefObject.uploadInstant).child(fileRefObject.name)
        window.open(fileReference.getDownloadUrl())
    }
}
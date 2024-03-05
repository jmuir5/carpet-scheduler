package com.noxapps.carpetScheduler.jobPage

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

class JobViewModel(
    jobId:String,
    app: FirebaseApp,
    val navController: FauxNavController,
    val coroutineScope: CoroutineScope
) {

    val database = Firebase.database(app).reference()
    val storage = Firebase.storage(app).reference
    val thisJob: MutableState<TrueJobObject> = mutableStateOf(TrueJobObject())

    val jobOrg:MutableState<Organization> = mutableStateOf(Organization())
    var jobImages = mutableStateListOf<String>()
    val imagesLoaded = mutableStateOf(false)

    val recomposeBit =mutableStateOf(false)



    init {


        getJobOrgImgFromId(jobId,thisJob,jobOrg,jobImages,imagesLoaded, recomposeBit)
        /*println(jobId)
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println(throwable)
        }
        coroutineScope.launch(handler) {
            val jobs = database.child("Jobs").orderByKey().equalTo(jobId).valueEvents.first().children

            jobs.forEach {
                val job = it.value(TrueJobObject.serializer())
                println("job: $job")
                val org = getOrgFromId(database, job.agent.organisation)
                MainScope().launch {
                    thisJob.value = job
                    jobOrg.value = org
                }
                val images = mutableListOf<String>()
                job.extraInformationObject.images.forEach {file->
                    images.add(storage.child(file.type).child(file.uploadInstant).child(file.name).getDownloadUrl())
                }
                MainScope().launch {
                    jobImages = images.toMutableStateList()
                    imagesLoaded.value = true
                }

            }

        }*/

    }
    fun getJobOrgImgFromId(
        jobId:String,
        jobTarget:MutableState<TrueJobObject>,
        orgTarget:MutableState<Organization>,
        imageTarget: SnapshotStateList<String>,
        imageFlag: MutableState<Boolean>,
        overallFlag:MutableState<Boolean>
    ){
        println(jobId)
        overallFlag.value = false
        jobTarget.value = TrueJobObject()
        orgTarget.value = Organization()
        imageTarget.clear()
        imageFlag.value = false
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println(throwable)
        }
        coroutineScope.launch(handler) {
            val jobs = database.child("Jobs").orderByKey().equalTo(jobId).valueEvents.first().children

            jobs.forEach {
                val job = it.value(TrueJobObject.serializer())
                println("job: $job")
                val org = getOrgFromId(database, job.agent.organisation)
                MainScope().launch {
                    jobTarget.value = job
                    orgTarget.value = org
                    overallFlag.value = true
                }
                val images = mutableListOf<String>()
                job.extraInformationObject.images.forEach {file->
                    images.add(storage.child(file.type).child(file.uploadInstant).child(file.name).getDownloadUrl())
                }
                MainScope().launch {
                    images.forEach {image->
                        imageTarget.add(image)
                    }
                    imageFlag.value = true
                }

            }

        }
    }

    suspend fun downloadFile(fileRefObject: FileRefObject){
        val fileReference = storage.child(fileRefObject.type).child(fileRefObject.uploadInstant).child(fileRefObject.name)
        window.open(fileReference.getDownloadUrl())
    }


    fun saveChanges(jobObject:TrueJobObject){
        //recomposeBit.value = false
        println("save Changes initiated")
        coroutineScope.launch() {
            println("coroutine launched, saving changes")

            database.child("Jobs").child(jobObject.id).setValue(jobObject)
            MainScope().launch {
                println("mainScope Launched, repulling data")
                thisJob.value = jobObject
                //recomposeBit.value = true
                //getJobOrgImgFromId(jobId, thisJob, jobOrg, jobImages, imagesLoaded,recomposeBit)
            }
        }
        //thisJob.value = jobObject
    }
}
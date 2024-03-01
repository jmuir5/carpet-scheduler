package com.noxapps.carpetScheduler.adminPanel.manageOrgsPage

import androidx.compose.runtime.MutableState
import com.noxapps.carpetScheduler.dataStructures.Organization
import com.noxapps.carpetScheduler.dataStructures.User
import com.noxapps.carpetScheduler.navigation.FauxNavController
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ManOrgsViewModel(
    val app: FirebaseApp,
    private val coroutineScope: CoroutineScope,
    private val navController: FauxNavController
) {
    val database = Firebase.database(app).reference()


    fun getOrgs(target: MutableList<Organization>, flag: MutableState<Boolean>){
        coroutineScope.launch {
            val orgs = database.child("Orgs").orderByKey()
                .valueEvents.first().children
            orgs.forEach {
                val org = it.value(Organization.serializer())
                println("org: $org")
                MainScope().launch { target.add(org) }
            }
            MainScope().launch {
                println("done")
                flag.value= !flag.value
            }

        }
    }

    fun saveOrg(saveable: Organization){
        coroutineScope.launch {
            database.child("Orgs").child(saveable.id).setValue(saveable)
        }
    }
}
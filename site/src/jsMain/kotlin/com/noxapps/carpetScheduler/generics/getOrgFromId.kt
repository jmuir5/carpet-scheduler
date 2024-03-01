package com.noxapps.carpetScheduler.generics

import com.noxapps.carpetScheduler.dataStructures.Organization
import dev.gitlive.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.first

suspend fun getOrgFromId(database: DatabaseReference, id:String): Organization {
    return try {
        database.child("Orgs").orderByKey().equalTo(id)
            .valueEvents.first().children.firstOrNull()?.value(Organization.serializer())?: Organization()
    } catch (_: Error) {
        Organization()
    }
}
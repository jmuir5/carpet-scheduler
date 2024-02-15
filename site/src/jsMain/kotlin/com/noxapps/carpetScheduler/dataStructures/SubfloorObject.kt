package com.noxapps.carpetScheduler.dataStructures

import kotlinx.serialization.Serializable

@Serializable
data class SubfloorObject(
    var short:String = "",
    var details:String = ""
) {

}

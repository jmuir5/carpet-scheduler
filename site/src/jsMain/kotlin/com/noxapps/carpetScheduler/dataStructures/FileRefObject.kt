package com.noxapps.carpetScheduler.dataStructures

import kotlinx.serialization.Serializable

@Serializable
data class FileRefObject(
    var type:String = "",
    var uploadInstant:String = "",
    var name:String = "",
    var size:String = "",
    var status:String = "N/A"
) {

}

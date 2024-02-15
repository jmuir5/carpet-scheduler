package com.noxapps.carpetScheduler.dataStructures

import kotlinx.serialization.Serializable
import kotlinx.serialization.StringFormat

@Serializable
data class ClientObject(
    var name:String = "",
    var phone:String = "",
    var address: String =""
){

}

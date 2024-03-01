package com.noxapps.carpetScheduler.dataStructures

import kotlinx.serialization.Serializable

@Serializable
data class Organization(
    val id:String = "",
    var name:String = "",
    var phone:String = "",
    var address:String = "",
    var code:String = ""
) {
}

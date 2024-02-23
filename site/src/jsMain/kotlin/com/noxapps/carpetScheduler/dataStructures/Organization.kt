package com.noxapps.carpetScheduler.dataStructures

import kotlinx.serialization.Serializable

@Serializable
data class Organization(
    val name:String = "",
    val phone:String = "",
    val address:String = ""
) {



}

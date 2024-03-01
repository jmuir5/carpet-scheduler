package com.noxapps.carpetScheduler.dataStructures

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val UUID:String = "",
    var organisation:String = "",
    var name: String = "",
    var surname: String = "",
    var phone: String = ""
) {
}
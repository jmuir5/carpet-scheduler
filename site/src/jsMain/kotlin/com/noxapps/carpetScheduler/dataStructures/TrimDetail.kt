package com.noxapps.carpetScheduler.dataStructures

import kotlinx.serialization.Serializable

@Serializable
data class TrimDetail(
    var presence:Boolean = false,
    var detail:String? = null
) {

}

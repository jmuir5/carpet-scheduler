package com.noxapps.carpetScheduler.dataStructures

import kotlinx.serialization.Serializable

@Serializable
data class AccessDetail(
    var short:String = "",
    var details:String? = null
)

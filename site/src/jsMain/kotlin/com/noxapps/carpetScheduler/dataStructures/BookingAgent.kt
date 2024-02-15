package com.noxapps.carpetScheduler.dataStructures

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class BookingAgent (
    var company:String = "",
    var branch:String? = null,
    //val agentName:String,
    //val AgentNumber:String
)

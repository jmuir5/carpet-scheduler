package com.noxapps.carpetScheduler.dataStructures

import androidx.compose.runtime.mutableStateOf
import kotlinx.serialization.Serializable

@Serializable
data class StairsObject(
    var present:Boolean = false,
    var straight:Int = 0,
    var winders :Int = 0,
    var bullnose :Int = 0
) {

}

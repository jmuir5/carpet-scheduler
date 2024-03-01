package com.noxapps.carpetScheduler.dataStructures

import kotlinx.serialization.Serializable

@Serializable
data class ExtraInformationObject(
    var subfloor:SubfloorObject = SubfloorObject(),
    var smoothedge:Boolean = false,
    var waterDamage:Boolean =false,
    var dramas:String = "",
    var additionalInfo:String = "",
    var images:List<FileRefObject> = listOf()
) {
}
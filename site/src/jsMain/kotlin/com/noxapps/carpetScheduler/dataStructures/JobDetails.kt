package com.noxapps.carpetScheduler.dataStructures

import kotlinx.serialization.Serializable

@Serializable
data class JobDetails(
    var invoiceNumber:String = "",
    var carpetId:String = "",
    var meterage:Float = 0f,
    var takeup:Boolean = false,
    var furniture:String = "",
    var underlay:String = "",
    var stairs:StairsObject = StairsObject(),
    var access:AccessDetail = AccessDetail(),
    var trim:TrimDetail =TrimDetail()
) {

}

package com.noxapps.carpetScheduler.dataStructures

import kotlinx.serialization.Serializable

@Serializable
data class TrueJobObject (
    var id:String = "",
    var schedule:DateObject = DateObject(),
    var agent:BookingAgent = BookingAgent(),
    var client:ClientObject = ClientObject(),
    var cutSheet:FileRefObject = FileRefObject(),
    var floorPlan:FileRefObject = FileRefObject(),
    var jobDetails:JobDetails = JobDetails(),
    var extraInformationObject: ExtraInformationObject = ExtraInformationObject()
){
    fun size():Int{
        var size = 0
        when(true){
            (0f.rangeTo(5f).contains(jobDetails.meterage)) ->size+=2
            (5f.rangeTo(10f).contains(jobDetails.meterage)) ->size+=3
            (10f.rangeTo(15f).contains(jobDetails.meterage)) ->size+=4
            (15f.rangeTo(20f).contains(jobDetails.meterage)) ->size+=5
            (20f.rangeTo(25f).contains(jobDetails.meterage)) ->size+=6
            (25f.rangeTo(30f).contains(jobDetails.meterage)) ->size+=8
            (jobDetails.meterage>30f) ->size+=10
            else -> size+=0
        }
        if(jobDetails.takeup)size+=2
        when(jobDetails.furniture){
            "Heavy"->size+=3
            "Light"->size+=2
        }
        if(jobDetails.stairs.present)size+=2
        if(jobDetails.access.short!="Good") size+=1
        return size
    }
}
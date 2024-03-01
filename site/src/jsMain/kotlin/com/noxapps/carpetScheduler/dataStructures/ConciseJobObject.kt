package com.noxapps.carpetScheduler.dataStructures

import com.varabyte.kobweb.compose.ui.graphics.Color


class ConciseJobObject (
    val id:String,
    val organisation:String,
    val title:String,
    val size:Int,

    //val data:String
){
    fun weight():Float{
        return when(true){
            (size>=10) -> 4f
            (size>=7) -> 3f
            (size>=4) -> 2f
            else ->1f
        }
    }
    fun color():Color{
        return when(this.weight()){
            1f -> Color.rgb(255,0,0)
            2f -> Color.rgb(0,255,0)
            3f -> Color.rgb(0,0,255)
            4f -> Color.rgb(255,255,0)
            else-> Color.rgb(255,255,255)
        }

    }
    fun height(size:Int =this.weight().toInt() ):Int {
        return ((size*25)+(4*(size-1)))
    }
    constructor(job:TrueJobObject) : this(
        id = job.id,
        organisation = job.agent.organisation,
        title = job.jobDetails.invoiceNumber,
        size = job.size()
    )
}
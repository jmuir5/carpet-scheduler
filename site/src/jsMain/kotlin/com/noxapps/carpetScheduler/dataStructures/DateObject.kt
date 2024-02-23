package com.noxapps.carpetScheduler.dataStructures

import kotlinx.serialization.Serializable

@Serializable
data class DateObject (
    var day:Int = 0,
    var month:Int = 0,
    var year:Int = 1970
):Comparable<DateObject>{
    override operator fun compareTo(b:DateObject):Int {
        return fauxHash() - b.fauxHash()
    }

    private fun fauxHash():Int{
        return this.year*1000+this.month*100+this.day
    }

    override fun toString(): String {
        return "$day/$month/$year"
    }

}

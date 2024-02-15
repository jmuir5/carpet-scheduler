package com.noxapps.carpetScheduler.navigation

class Routes() {
    val forumPage = "Forum"
    val calendarPage = "Calendar"
    val previewCal = "Preview"

    fun contains(s:String):Boolean{
        return s==forumPage||s==calendarPage||s==previewCal
    }
}
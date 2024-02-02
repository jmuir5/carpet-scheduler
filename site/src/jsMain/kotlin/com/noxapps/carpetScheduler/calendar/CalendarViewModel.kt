package com.noxapps.carpetScheduler.calendar


import com.noxapps.carpetScheduler.JobObject
import androidx.compose.runtime.MutableState
import kotlinx.datetime.*
import kotlin.random.Random


class CalendarViewModel {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

    var currentMonth = today.month
    var currentDay = today.dayOfMonth
    var currentYear =today.year


    var currentMonthCalendar = monthArray(currentMonth.number)

    fun monthArray(month:Int, year:Int =Clock.System.todayIn(TimeZone.currentSystemDefault()).year):List<List<Int>>{
        val firstDay = LocalDate(year, month, 1).dayOfWeek
        var dayCounter = 0
        var i=0
        var j=dayOfWeekOffset(firstDay.toString())
        val roughMonth = (0..4).map{mutableListOf(0,0,0,0,0,0,0)}
        val returnList = mutableListOf<List<Int>>()
        while (dayCounter<monthLength(month, year)){
            dayCounter++
            roughMonth[i][j] = dayCounter
            j = (j + 1) % 7
            if (j == 0) i = (i + 1) % 5

        }
        for (k in 0..4) returnList.add(roughMonth[k].toList())
        return returnList.toList()
    }
    fun monthLength(month:Int, year:Int =Clock.System.todayIn(TimeZone.currentSystemDefault()).year):Int{
        return when(month){
            1 -> 31
            2 -> {if(year%4==0)29 else 28}
            3 -> 31
            4 -> 30
            5 -> 31
            6 -> 30
            7 -> 31
            8 -> 31
            9 -> 30
            10 -> 31
            11 -> 30
            12 -> 31
            else -> -1
        }
    }

    fun dayOfWeekOffset(dayOfWeek: String):Int{
        return when(dayOfWeek){
            "MONDAY" -> 1
            "TUESDAY" -> 2
            "WEDNESDAY" -> 3
            "THURSDAY" -> 4
            "FRIDAY" -> 5
            "SATURDAY" -> 6
            "SUNDAY" -> 0
            else -> -1

        }
    }

    fun nextMonth(
        month: MutableState<Month>,
        year:MutableState<Int>,
        calendar:MutableState<List<List<Int>>>
    ){
        month.value = Month(month.value.number%12+1)
        if (month.value.number==1){
            year.value++
        }
        calendar.value = monthArray((month.value.number), year.value)
    }


    fun prevMonth(
        month: MutableState<Month>,
        year:MutableState<Int>,
        calendar:MutableState<List<List<Int>>>
    ){
        val monthHolder = (month.value.number-2)%12+1
        if (monthHolder==0){
            month.value = Month(12)
            year.value--
        }
        else {
            month.value = Month(monthHolder)
        }
        calendar.value = monthArray(month.value.number, year = year.value)
    }

    fun getJobsByDay(day:Int):List<JobObject>{
        // TODO: this
        val job1U = JobObject("test 1", 3, "a")
        val job2U = JobObject("test 2", 6, "a")
        val job3U = JobObject("test 3", 9, "a")
        val job4U = JobObject("test 4", 12, "a")
        val returnList = mutableListOf<JobObject>()
        var round = 0
        var total = 0
        while (round<6&&total<13){
            when(Random.nextInt(1,5)) {
                1 -> {
                    if (total + 3 < 13) {
                        returnList.add(job1U)
                        total += 3
                    }
                    round += 1
                }

                2 -> {
                    if (total + 6 < 13) {
                        returnList.add(job2U)
                        total += 6
                    }
                    round += 1
                }

                3 -> {
                    if (total + 9 < 13) {
                        returnList.add(job3U)
                        total += 9
                    }
                    round += 1
                }

                4 -> {
                    if (total + 12 < 13) {
                        returnList.add(job4U)
                        total += 12
                    }
                    round += 1
                }
            }
        }
        return returnList
    }
}
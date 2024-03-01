package com.noxapps.carpetScheduler.adminPanel

import androidx.compose.runtime.MutableState
import com.noxapps.carpetScheduler.dataStructures.DateObject
import com.noxapps.carpetScheduler.dataStructures.Organization
import com.noxapps.carpetScheduler.dataStructures.TrueJobObject
import com.noxapps.carpetScheduler.dataStructures.User
import com.noxapps.carpetScheduler.navigation.FauxNavController
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseApp
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.todayIn

class PanelViewModel(
    val coroutineScope: CoroutineScope,
    val app: FirebaseApp,
    val user: MutableState<User>,
    val userOrg: MutableState<Organization>,
    val navController: FauxNavController
) {
    val database = Firebase.database(app).reference()

    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

    var todayMonth = today.month
    var todayDay = today.dayOfMonth
    var todayYear =today.year

    var todayDateObject = DateObject(todayDay, todayMonth.number, todayYear)

    val accountType = when(user.value.organisation){
        "0"->"Admin"
        "1"->"Tech"
        else->"Shop"
    }




    fun getJobs(target: List<MutableList<TrueJobObject>>, flag: MutableState<Boolean>){
        coroutineScope.launch {
            val jobs = when(user.value.organisation){
                "0",  "1"->{
                    database.child("Jobs").orderByChild("schedule/month")
                        .valueEvents.first().children
                }
                else->{
                    database.child("Jobs").orderByChild("agent/organisation").equalTo(user.value.organisation)
                        .valueEvents.first().children
                }

            }

            jobs.forEach {
                val job = it.value(TrueJobObject.serializer())
                println("job: $job")
                if (job.schedule>=todayDateObject){
                    if(job.schedule==todayDateObject) {
                        MainScope().launch { target[0].add(job) }
                    }
                    else if(withinWeek(job.schedule, todayDateObject)) {
                        MainScope().launch { target[1].add(job) }
                    }
                    else {
                        MainScope().launch { target[2].add(job) }
                    }
                }
            }
            MainScope().launch {
                target[0].sortBy { it.schedule }
                target[1].sortBy { it.schedule }
                target[2].sortBy { it.schedule }
                flag.value=true
            }

        }
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

    fun withinWeek(targetDate:DateObject, startDate:DateObject):Boolean{
        if(targetDate<=startDate) return false
        else if((targetDate.month==startDate.month||targetDate.month==startDate.month+1||targetDate.month==1) &&
            (targetDate.year == startDate.year||targetDate.year == startDate.year+1)) {
            (1..7).forEach {
                var newDay = startDate.day+it
                var newMonth = startDate.month
                var newYear = startDate.year
                if(newDay>monthLength(startDate.month)){
                    newDay %= monthLength(startDate.month)
                    if(newDay==0) newDay =monthLength(startDate.month)
                    if(newMonth==12){
                        newMonth=1
                        newYear+=1
                    }
                    else newMonth+=1
                }
                if(targetDate== DateObject(newDay, newMonth, newYear))return true
            }
        }
        return false

    }

    fun signOut(){
        coroutineScope.launch {
            Firebase.auth(app).signOut()
            MainScope().launch {
                user.value = User()
                userOrg.value = Organization()
            }
        }
        navController.popBackStack()

    }
}
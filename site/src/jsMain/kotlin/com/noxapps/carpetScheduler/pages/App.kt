package com.noxapps.carpetScheduler.pages

import Forum.Forum
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import Calendar.CalendarPage
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        val forumState = remember { mutableStateOf(true) }
        val calendarState = remember{mutableStateOf(false)}
        val currentJob = remember{mutableStateOf(false)}
        if (forumState.value){
            Forum(forumState, currentJob)
        }
        else {
            CalendarPage(forumState, currentJob)
        }


    }
}
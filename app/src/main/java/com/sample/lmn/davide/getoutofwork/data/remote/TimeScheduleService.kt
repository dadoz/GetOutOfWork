package com.sample.lmn.davide.getoutofwork.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sample.lmn.davide.getoutofwork.models.TimeSchedule

class TimeScheduleService {
    private val firebaseReference: DatabaseReference by lazy {
        Firebase.database.reference
    }

    fun getTimeSchedule(): LiveData<List<TimeSchedule>> {
        return FirebaseQueryLiveData(firebaseReference).map {
            dataSnapshot -> dataSnapshot?.value as List<TimeSchedule>
        }
    }

    fun addTimeSchedule(timeSchedule: TimeSchedule) {
        val userId = timeSchedule.userId ?: "-1"
        firebaseReference.child("time_schedule").child(userId).apply {
            push().key?.let {
                timeSchedule.id = it
                this.child(it).setValue(timeSchedule)
            }
        }
    }
}
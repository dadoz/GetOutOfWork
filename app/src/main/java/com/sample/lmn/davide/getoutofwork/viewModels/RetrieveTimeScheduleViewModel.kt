package com.sample.lmn.davide.getoutofwork.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.sample.lmn.davide.getoutofwork.data.TimeScheduleRepository
import com.sample.lmn.davide.getoutofwork.models.TimeSchedule
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.coroutineContext

class RetrieveTimeScheduleViewModel(application: Application): AndroidViewModel(application) {
    //    private val scheduleTimeList: LiveData<List<TimeSchedule>> =  liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
//        emit(database.loadUserById(id))
//    }

    val repository = TimeScheduleRepository(application)

    var timeSchedule: MutableLiveData<List<TimeSchedule>> = MutableLiveData()

    fun getTimeSchedule(): LiveData<List<TimeSchedule>> {
        return liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(repository.getTimeSchedule())
        }.map { list -> this.timeSchedule.value = list.value; list.value!! }
    }

    fun addTimeSchedule(timeSchedule: TimeSchedule): LiveData<List<TimeSchedule>> {
        return repository.addTimeSchedule(timeSchedule).switchMap {
            repository.getTimeSchedule()
        }.map { list -> this.timeSchedule.value = list; list }
    }

}
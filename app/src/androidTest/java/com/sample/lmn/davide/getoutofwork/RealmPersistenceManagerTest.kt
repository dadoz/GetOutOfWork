package com.sample.lmn.davide.getoutofwork

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.sample.lmn.davide.getoutofwork.managers.RealmPersistenceManager
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by davide-syn on 7/25/17.
 */

@RunWith(AndroidJUnit4::class)
class RealmPersistenceManagerTest {
    @Rule @JvmField
    var mainActivityActivityTestRule = ActivityTestRule(MainActivity::class.java)

    lateinit var realmInstance: RealmPersistenceManager

    @Before
    fun init() {
        realmInstance = RealmPersistenceManager.Holder(mainActivityActivityTestRule.activity).instance
    }

    @Test
    fun initTodayNotNullTest() {
        assertNotNull(realmInstance.initTodayTimeSchedule())
    }

    @Test
    fun findTodayTest() {
        //create today schedule
        realmInstance.initTodayTimeSchedule()

        //assert
        assertNotNull(realmInstance.getTodayTimeSchedule())
    }

    @Test
    fun createTodaySchedule() {
        //assert
        assertNotNull(realmInstance.createTodayTimeSchedule())
    }

    @Test
    fun checkInTestAM() {
        //assert
        realmInstance.checkToday()

        assertNotNull(realmInstance.getTodayTimeSchedule())
        assertNotNull(realmInstance.getTodayTimeSchedule().checkInDateAm)
    }

    @Test
    fun checkOutTestAM() {
        //assert
        //assert
        realmInstance.checkToday()

        assertNotNull(realmInstance.getTodayTimeSchedule())
        assertNotNull(realmInstance.getTodayTimeSchedule().checkOutDateAm)
    }

    @Test
    fun checkInTestPM() {
        //assert
        realmInstance.checkToday()

        assertNotNull(realmInstance.getTodayTimeSchedule())
        assertNotNull(realmInstance.getTodayTimeSchedule().checkInDateAm)
    }

    @Test
    fun checkOutTestPM() {
        //assert
        //assert
        realmInstance.checkToday()

        assertNotNull(realmInstance.getTodayTimeSchedule())
        assertNotNull(realmInstance.getTodayTimeSchedule().checkOutDateAm)
    }

    @Test
    fun isCheckInTestAMTest() {
        realmInstance.checkToday()
        assertEquals(realmInstance.isCheckedInToday(Calendar.AM), true)
    }
    @Test
    fun isCheckInTestPMTest() {
        realmInstance.checkToday()
        assertEquals(realmInstance.isCheckedInToday(Calendar.PM), true)
    }
    @Test
    fun isCheckOutTestAMTest() {
        realmInstance.checkToday()
        assertEquals(realmInstance.isCheckedOutToday(Calendar.AM), true)
    }
    @Test
    fun isCheckOutTestPMTest() {
        realmInstance.checkToday()
        assertEquals(realmInstance.isCheckedOutToday(Calendar.PM), true)
    }
}

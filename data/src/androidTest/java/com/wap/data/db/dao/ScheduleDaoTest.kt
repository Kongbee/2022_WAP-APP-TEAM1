package com.wap.data.db.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.wap.data.db.AppDatabase
import com.wap.data.entity.UserEntity
import com.wap.data.toEntity
import com.wap.domain.entity.Schedule
import com.wap.domain.entity.WeekType
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class ScheduleDaoTest {

    private lateinit var db: AppDatabase

    private lateinit var scheduleDao: ScheduleDao

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        db.userDao()
            .insertUser(UserEntity(1L, ""))

        scheduleDao = db.scheduleDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun when_findSchedulesByUserId_then_success() {
        // Given insert schedule
        val schedule = Schedule(
            scheduleId = 1L,
            startTime = LocalDateTime.of(2022, 5, 5, 12, 0),
            endTime = LocalDateTime.of(2022, 5, 5, 13, 0),
            color = "",
            recurWeek = WeekType.FRI,
            userId = 1L
        ).toEntity()

        scheduleDao.insertSchedule(schedule)

        // When fetch Schedule
        val schedules = scheduleDao.findSchedulesByUserId(1L)

        // Then equal initial schedule
        assertEquals(listOf(schedule), schedules)
    }

    @Test
    fun when_updateSchedule_then_success() {
        // Given insert schedule & modified schedule
        val schedule = Schedule(
            scheduleId = 1L,
            startTime = LocalDateTime.of(2022, 5, 5, 12, 0),
            endTime = LocalDateTime.of(2022, 5, 5, 13, 0),
            color = "",
            recurWeek = WeekType.FRI,
            userId = 1L
        ).toEntity()

        val modifiedSchedule = schedule.copy(
            color = "blue"
        )

        scheduleDao.insertSchedule(schedule)

        // When update schedule`s color
        scheduleDao.updateSchedule(modifiedSchedule)

        // Then schedule was updated
        assertEquals(modifiedSchedule, scheduleDao.findScheduleByScheduleId(schedule.scheduleId))
    }
}

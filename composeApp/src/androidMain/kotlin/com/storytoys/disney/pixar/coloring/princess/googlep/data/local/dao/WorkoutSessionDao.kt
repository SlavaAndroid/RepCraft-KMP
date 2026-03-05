package com.storytoys.disney.pixar.coloring.princess.googlep.data.local.dao

import androidx.room.*
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.entity.WorkoutSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSessionDao {
    @Query("SELECT * FROM workout_session ORDER BY completedAt DESC")
    fun observeAll(): Flow<List<WorkoutSessionEntity>>

    @Query("SELECT * FROM workout_session ORDER BY completedAt DESC LIMIT 10")
    suspend fun getRecent(): List<WorkoutSessionEntity>

    @Insert
    suspend fun insert(entity: WorkoutSessionEntity): Long

    @Query("SELECT completedAt FROM workout_session ORDER BY completedAt DESC")
    suspend fun getAllCompletedDates(): List<Long>
}
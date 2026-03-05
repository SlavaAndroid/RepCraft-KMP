package com.storytoys.disney.pixar.coloring.princess.googlep.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.dao.UserProfileDao
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.dao.WorkoutSessionDao
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.entity.UserProfileEntity
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.entity.WorkoutSessionEntity

@Database(
    entities = [UserProfileEntity::class, WorkoutSessionEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun workoutSessionDao(): WorkoutSessionDao
}
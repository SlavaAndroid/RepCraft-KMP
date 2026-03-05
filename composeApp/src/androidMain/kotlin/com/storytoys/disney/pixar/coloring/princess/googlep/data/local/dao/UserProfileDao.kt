package com.storytoys.disney.pixar.coloring.princess.googlep.data.local.dao

import androidx.room.*
import com.storytoys.disney.pixar.coloring.princess.googlep.data.local.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun observe(): Flow<UserProfileEntity?>

    @Upsert
    suspend fun upsert(entity: UserProfileEntity)
}
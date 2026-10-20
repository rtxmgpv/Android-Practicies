package com.example.android_practices.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE id = :postId")
    suspend fun removeFavorite(postId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :postId)")
    fun isFavorite(postId: Int): Flow<Boolean>
}
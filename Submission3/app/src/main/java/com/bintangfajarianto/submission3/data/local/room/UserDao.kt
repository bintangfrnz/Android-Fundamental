package com.bintangfajarianto.submission3.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bintangfajarianto.submission3.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getFavoriteUsers(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorites(users: UserEntity)

    @Query("DELETE FROM user WHERE username = :username")
    suspend fun removeFromFavorites(username: String)

    @Query("DELETE FROM user")
    suspend fun removeAll()

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username)")
    fun isUserFavorite(username: String): Boolean
}
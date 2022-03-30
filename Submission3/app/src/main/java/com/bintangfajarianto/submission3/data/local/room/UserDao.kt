package com.bintangfajarianto.submission3.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bintangfajarianto.submission3.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUser(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user where favorite = 1")
    fun getBookmarkedNews(): LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(news: List<UserEntity>)

    @Update
    suspend fun updateUser(news: UserEntity)

    @Query("DELETE FROM user WHERE favorite = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username AND favorite = 1)")
    suspend fun isUserFavorite(username: String): Boolean
}
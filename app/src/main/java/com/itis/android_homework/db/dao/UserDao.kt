package com.itis.android_homework.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.itis.android_homework.db.entity.UserEntity
import com.itis.android_homework.db.entity.tuple.UserAddressTuple
import com.itis.android_homework.db.entity.tuple.UserIdTuple

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(userData: UserEntity)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>?

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserInfoById(userId: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserInfoByEmail(email: String): UserEntity?

    @Query("UPDATE users SET email = :emailAddress WHERE id = :id")
    suspend fun updateUserAddressQuery(id: String, emailAddress: String)

    @Delete(entity = UserEntity::class)
    suspend fun deleteUserById(id: UserIdTuple)

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUserByIdQuery(id: String)
}
package com.itis.android_homework.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.itis.android_homework.db.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(userData: UserEntity)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>?

    @Query("SELECT * FROM users WHERE user_id = :userId")
    suspend fun getUserInfoById(userId: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserInfoByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email OR phone_number = :phoneNumber")
    suspend fun getUserInfoByEmailAndPhoneNumber(email: String, phoneNumber: String): UserEntity?

    @Query("UPDATE users SET email = :emailAddress WHERE user_id = :userId")
    suspend fun updateUserAddressQuery(userId: String, emailAddress: String)

    @Query("UPDATE users SET phone_number = :newPhoneNumber WHERE user_id = :userId")
    suspend fun updateUserPhoneNumber(userId: String, newPhoneNumber: String)

    @Query("UPDATE users SET password = :newPassword WHERE user_id = :userId")
    suspend fun updateUserPassword(userId: String, newPassword: String)

    @Query("UPDATE users SET deletion_time = :deletionTime WHERE user_id = :userId")
    suspend fun updateUserDeletionTime(userId: String, deletionTime: Long?)

    @Query("DELETE FROM users WHERE user_id = :id")
    suspend fun deleteUserById(id: String)

    @Query("SELECT * FROM users WHERE phone_number = :phoneNumber")
    suspend fun getUserInfoByPhoneNumber(phoneNumber: String) : UserEntity?
}
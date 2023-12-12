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
    fun addUser(userData: UserEntity)

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<UserEntity>?

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserInfoById(userId: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserInfoByEmail(email: String): UserEntity?

    @Update(entity = UserEntity::class)
    fun updateUserAddress(data: UserAddressTuple)

    @Query("UPDATE users SET email = :emailAddress WHERE id = :id")
    fun updateUserAddressQuery(id: String, emailAddress: String)

    @Delete(entity = UserEntity::class)
    fun deleteUserById(id: UserIdTuple)

    @Query("DELETE FROM users WHERE id = :id")
    fun deleteUserByIdQuery(id: String)
}
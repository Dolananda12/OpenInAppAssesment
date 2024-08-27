package com.example.openinappassesment.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TokenDAO {
@Query("SELECT * FROM tokentable")
fun getToken() : LiveData<Token>
@Update
suspend fun updateToken(token: Token)
@Insert
suspend fun insertToken(token: Token)
@Delete
suspend fun deleteToken(token: Token)
}
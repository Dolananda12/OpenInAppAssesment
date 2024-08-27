package com.example.openinappassesment.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("TokenTable")
data class Token(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("token")
    val token : String
)

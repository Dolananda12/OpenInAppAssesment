package com.example.openinappassesment.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities =[Token::class], version = 1, exportSchema = true)
abstract class TokenDatabase : RoomDatabase(){
    abstract val dao: TokenDAO
    companion object{
        @Volatile
        private var INSTANCE: TokenDatabase?=null
        fun getInstance(context: Context):TokenDatabase{
            synchronized(this){
                var instance= INSTANCE
                if(instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        TokenDatabase::class.java,
                        "TokenDatabase"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE=instance
                }
                return  instance
            }
        }
    }
}
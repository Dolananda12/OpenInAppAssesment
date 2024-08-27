package com.example.openinappassesment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.openinappassesment.Database.TokenRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val repository: TokenRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainActivityViewModel::class.java)){
            return MainActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
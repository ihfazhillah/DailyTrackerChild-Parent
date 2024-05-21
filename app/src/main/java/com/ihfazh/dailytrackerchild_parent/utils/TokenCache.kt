package com.ihfazh.dailytrackerchild_parent.utils

import android.content.SharedPreferences

class TokenCache(
//    private val sharedPreferences: SharedPreferences
){
    private var _token : String? = null


    fun saveToken(token: String){
        _token = token

    }

    fun getToken(): String?{
        return _token
    }

}
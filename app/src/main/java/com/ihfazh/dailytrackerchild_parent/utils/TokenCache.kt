package com.ihfazh.dailytrackerchild_parent.utils

import android.content.SharedPreferences

class TokenCache(
    private val sharedPreferences: SharedPreferences
){

    private val tokenKey = "userToken"

    fun saveToken(token: String){
        sharedPreferences.edit()
            .putString(tokenKey, token)
            .apply()
    }

    fun getToken(): String?{
        return sharedPreferences.getString(tokenKey, null)
    }

}
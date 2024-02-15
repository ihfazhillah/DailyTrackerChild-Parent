package com.ihfazh.dailytrackerchild_parent.utils

import android.content.SharedPreferences
import com.ihfazh.dailytrackerchild_parent.components.ProfileItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class ChildrenCache(
    private val sharedPreferences: SharedPreferences
){

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @OptIn(ExperimentalStdlibApi::class)
    private val profileAdapter = moshi.adapter<List<ProfileItem>>()

    fun saveProfiles(profiles: List<ProfileItem>){
        val profilesString = profileAdapter.toJson(profiles)
        sharedPreferences.edit()
            .putString("children", profilesString)
            .apply()
    }

    fun getProfile(id: String): ProfileItem?{
        val json = sharedPreferences.getString("children", null) ?: return null
        val profiles = profileAdapter.fromJson(json) ?: return null
        return profiles.find { profile -> profile.id == id }
    }

}
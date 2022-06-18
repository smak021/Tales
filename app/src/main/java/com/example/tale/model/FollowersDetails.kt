package com.example.tale.model

class FollowersDetails {
    private var followerName: String? = null
    private var followerEmail: String? = null

    fun setfollowerName(followerName: String?){
        this.followerName = followerName
    }

    fun getfollowerName(): String?{
        return followerName
    }

    fun getfollowerEmail(): String? {
        return followerEmail
    }

    fun setfollowerEmail(followerEmail: String?) {
        this.followerEmail = followerEmail
    }
}
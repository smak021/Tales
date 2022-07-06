package com.example.tale.model

import android.text.BoringLayout
import java.io.Serializable


class UserDetails: Serializable {
    private var full_name: String? = null
    private var email: String? = null
    private var phone: String? = null
    private var userPic:String? = null
    private var isVerified:Boolean = false

    fun getisVerified():Boolean{
        return isVerified
    }

    fun getuserPic(): String? {
        return userPic
    }

    fun setuserPic(userPic: String?) {
        this.userPic = userPic
    }
    fun getfull_name(): String? {
        return full_name
    }

    fun setfull_name(firstName: String?) {
        this.full_name = firstName
    }

    fun getemail(): String? {
        return email
    }

    fun setemail(email: String?) {
        this.email = email
    }

    fun getphone(): String? {
        return phone
    }

    fun setphone(phoneNo: String) {
        this.phone = phoneNo
    }
}
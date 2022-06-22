package com.example.tale.model

class UserDetails {
    private var full_name: String? = null
    private var email: String? = null
    private var phone: String? = null


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
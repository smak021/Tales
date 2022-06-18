package com.example.tale.model

class UserDetails {
    private var firstName: String? = null
    private var email: String? = null
    private var phoneNo: String? = null
    private var dp: String? = null


    fun getfirstName(): String? {
        return firstName
    }

    fun setfirstName(firstName: String?) {
        this.firstName = firstName
    }

    fun getemail(): String? {
        return email
    }

    fun setemail(email: String?) {
        this.email = email
    }

    fun getdp(): String? {
        return dp
    }

    fun setdp(dp: String?) {
        this.dp = dp
    }

    fun getphoneNo(): String? {
        return phoneNo
    }

    fun setphoneNo(phoneNo: String?) {
        this.phoneNo = phoneNo
    }
}
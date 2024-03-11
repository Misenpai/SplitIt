package com.example.splitit

class HelperClass(
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var passwordConfirm: String? = null
) {

    constructor() : this("", "", "", "")
}
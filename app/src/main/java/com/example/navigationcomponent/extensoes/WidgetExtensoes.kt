package com.example.navigationcomponent.extensoes

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.dismissError(){
    this.error =null
    this.isErrorEnabled = false
}
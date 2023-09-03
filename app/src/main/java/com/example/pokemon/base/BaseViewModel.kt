package com.example.pokemon.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel:ViewModel() {
    private val _isShowDialog = MutableLiveData<Boolean>()
    private val _errorMessage = MutableLiveData<String>()

    val isShowDialog: LiveData<Boolean> = _isShowDialog

    val errorMessage: LiveData<String> = _errorMessage

    fun checkDialog() : Boolean {
        if (_isShowDialog.value == true) {
            return true
        } else {
            return false
        }
    }

    fun changeShow(newValue: Boolean) {
        _isShowDialog.postValue(newValue)
    }

    fun sendMessageError(newValue: String) {
        _errorMessage.postValue(newValue)
    }
}
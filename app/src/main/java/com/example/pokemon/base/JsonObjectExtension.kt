package com.example.pokemon.base

import retrofit2.Response

inline fun <T> Response<T>.handleResponseV2(
    onSuccess: (result: T) -> Unit,
    onFailed: (result: String) -> Unit,
) {
    try {
        if (this.code() == 200) {
            this.body()?.let { onSuccess.invoke(it) }
        } else {
            onFailed.invoke("Gagal Memuat Data")
        }
    } catch (e: Exception) {
        onFailed.invoke("Gagal Memuat Data")
    }

}

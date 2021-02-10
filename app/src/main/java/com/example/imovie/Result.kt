package com.example.imovie

sealed class Result <out D, out E> {
    data class Success <D> (val value: D) : Result<D, Nothing>()
    data class Error <E> (val value: E) : Result<Nothing, E>()
}
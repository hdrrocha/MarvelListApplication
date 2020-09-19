package com.example.marvellistapplication.api

import java.util.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*


interface MarvelApiApi {
    companion object {
        val ts = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
        const val URL = "https://gateway.marvel.com/v1/public/"
        const val API_KEY = "4271fa5b039c96fb82623d121abf1326"
        const val API_HASH = "dba4e3a0288d0a5fdddf64aa6bae3d80"
        const val LIMIT = "2"
        const val OFFSET = "0"
        // val TS = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        val TS = "1"
    }

   //TODO
}
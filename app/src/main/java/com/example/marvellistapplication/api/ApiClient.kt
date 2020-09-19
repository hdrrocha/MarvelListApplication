package com.example.marvellistapplication.api

import com.example.marvellistapplication.api.model.Response
import io.reactivex.Observable
import javax.inject.Inject


class ApiClient @Inject constructor(private val marvelApi: MarvelApiApi) {
    fun characters(apiKey: String, hashApi: String, offset: Long): Observable<Response> {
        return marvelApi.characters(apiKey, hashApi, offset)
    }

    fun charactersId(id: String, apiKey: String, hashApi: String): Observable<Response> {
        return marvelApi.charactersId(id, apiKey, hashApi)
    }
}
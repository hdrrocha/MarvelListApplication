package com.example.marvellistapplication.api.model

data class Data(
        val offset: Int,
        val limit: Int,
        val total: Int,
        val count: Int,
        val results: List<SuperHero>
)
package com.example.marvellistapplication.api.model

import com.example.helderrocha.marvelapplication.model.SuperHero


data class Data(
        val offset: Int,
        val limit: Int,
        val total: Int,
        val count: Int,
        val results: List<SuperHero>
)
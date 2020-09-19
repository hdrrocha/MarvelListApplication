package com.example.marvellistapplication.api.model

data class SuperHero(
        val id: Long,
        val name: String,
        val thumbnail: Thumbnail,
        val description: String,
        val comics: Comics)
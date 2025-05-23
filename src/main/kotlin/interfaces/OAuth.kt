package com.lecture.interfaces

interface OAuthServiceInterface{
    val providerName: String
    fun getToken(code: String): String
}

interface OAuth2TokenResponse{
    val accessToken: String
}

interface OAuth2UserResponse{
    val id: String
    val emial: String?
    val name: String?
}
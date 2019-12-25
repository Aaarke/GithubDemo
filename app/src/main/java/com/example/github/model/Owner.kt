package com.example.github.model

data class Owner(
    private val login: String,
    private val id: Integer,
    private val nodeId: String,
    private val avatarUrl: String,
    private val gravatarId: String,
    private val url: String,
    private val htmlUrl: String,
    private val followersUrl: String,
    private val followingUrl: String,
    private val gistsUrl: String,
    private val starredUrl: String,
    private val subscriptionsUrl: String,
    private val organizationsUrl: String,
    private val reposUrl: String,
    private val eventsUrl: String,
    private val receivedEventsUrl: String,
    private val type: String,
    private val siteAdmin: Boolean
)
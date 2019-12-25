package com.example.github.model


data class Item(
    val id: Int? = null,
    val nodeId: String? = null,
    val name: String? = null,
    val fullName: String? = null,
    private val _private: Boolean? = null,
    private val owner: Owner? = null,
    private val htmlUrl: String? = null,
    private val description: String? = null,
    private val fork: Boolean? = null,
    private val url: String? = null,
    private val forksUrl: String? = null,
    private val keysUrl: String? = null,
    private val collaboratorsUrl: String? = null,
    private val teamsUrl: String? = null,
    private val hooksUrl: String? = null,

    private val issueEventsUrl: String? = null,
    private val eventsUrl: String? = null,

    private val assigneesUrl: String? = null,

    private val branchesUrl: String? = null,
    private val tagsUrl: String? = null,
    private val blobsUrl: String? = null,
    private val gitTagsUrl: String? = null,
    private val gitRefsUrl: String? = null,

    private val treesUrl: String? = null,
    private val statusesUrl: String? = null
    ,
    private val languagesUrl: String? = null,
    private val stargazersUrl: String? = null,
    private val contributorsUrl: String? = null,
    private val subscribersUrl: String? = null,
    private val subscriptionUrl: String? = null,
    private val commitsUrl: String? = null,
    private val gitCommitsUrl: String? = null,
    private val commentsUrl: String? = null,
    private val issueCommentUrl: String? = null,
    private val contentsUrl: String? = null,
    private val compareUrl: String? = null,
    private val mergesUrl: String? = null,
    private val archiveUrl: String? = null,
    private val downloadsUrl: String? = null,
    private val issuesUrl: String? = null,
    private val pullsUrl: String? = null,
    private val milestonesUrl: String? = null,
    private val notificationsUrl: String? = null,
    private val labelsUrl: String? = null,
    private val releasesUrl: String? = null,

    private val deploymentsUrl: String? = null,

    private val createdAt: String? = null,

    private val updatedAt: String? = null,

    private val pushedAt: String? = null,

    private val gitUrl: String? = null,

    private val sshUrl: String? = null,

    private val cloneUrl: String? = null,

    private val svnUrl: String? = null,

    private val homepage: Any? = null,

    private val size: Int? = null,

    private val stargazersCount: Int? = null,

    private val watchersCount: Int? = null,

    private val language: String? = null,

    private val hasIssues: Boolean? = null,

    private val hasProjects: Boolean? = null,

    private val hasDownloads: Boolean? = null,

    private val hasWiki: Boolean? = null,

    private val hasPages: Boolean? = null,

    private val forksCount: Int? = null,

    private val mirrorUrl: Any? = null,

    private val archived: Boolean? = null,

    private val disabled: Boolean? = null,

    private val openIssuesCount: Int? = null,

    private val license: Any? = null,

    private val forks: Int? = null,

    private val openIssues: Int? = null,

    private val watchers: Int? = null,

    private val defaultBranch: String? = null,

    private val score: Double? = null

)
package com.example.github.utility

interface Keys {
    interface ApiField {
    companion object{
        const val REQ_Q: String="q"
        const val REQ_SORT="sort"
        const val REQ_ORDER="order"

    }
    }

    companion object{
    const val BASE_URL = "https://api.github.com"
}
}
package com.example.rssreader.model

data class RSSObject(val status: String, val feed: Feed, val items: List<Item>)
package com.example.openinappassesment.Network

data class Data(
    val favourite_links: List<String>,
    val overall_url_chart: HashMap<String,Int>?,
    val recent_links: List<RecentLink>?,
    val top_links: List<RecentLink>?
)
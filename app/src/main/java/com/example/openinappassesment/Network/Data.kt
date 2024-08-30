package com.example.openinappassesment.Network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val favourite_links: List<String>,
    val overall_url_chart: HashMap<String,Int>?,
    val recent_links: List<RecentLink>?,
    val top_links: List<RecentLink>?
) : Parcelable
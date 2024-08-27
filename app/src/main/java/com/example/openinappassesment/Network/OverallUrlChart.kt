package com.example.openinappassesment.Network

import kotlinx.serialization.Serializable

@Serializable
data class OverallUrlChart(
   val dataPoint :HashMap<String,Int>
)
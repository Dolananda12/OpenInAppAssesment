package com.example.openinappassesment.Network

data class DashboardObject(
    var today_clicks: Int=0,
    var top_location: String="",
    var top_source: String="",
    var total_clicks: Int=0,
    var total_links: Int=0,
    var applied_campaign: Int=0,
    var data: Data=Data(ArrayList(), HashMap(),ArrayList(),ArrayList()),
    var extra_income: Double=0.0,
    var links_created_today: Int=0,
    var message: String="",
    var startTime: String="",
    var status: Boolean=false,
    var statusCode: Int=200,
    var support_whatsapp_number: String=""
)
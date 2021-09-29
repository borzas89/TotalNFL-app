package example.com.totalnfl.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Bookmaker {
    @Json(name = "key")
    var key: String? = null

    @Json(name = "title")
    var title: String? = null

    @Json(name = "last_update")
    var lastUpdate: String? = null

    @Json(name = "markets")
    var markets: List<Market>? = null

}
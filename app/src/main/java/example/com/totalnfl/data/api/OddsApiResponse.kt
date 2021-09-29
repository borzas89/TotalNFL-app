package example.com.totalnfl.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class OddsApiResponse (
    @Json(name = "id")
    var id: String = "",

    @Json(name = "sport_key")
    var sportKey: String = "",

    @Json(name = "sport_title")
    var sportTitle: String = "",

    @Json(name = "commence_time")
    var commenceTime: String? = "",

    @Json(name = "home_team")
    var homeTeam: String? = "",

    @Json(name = "away_team")
    var awayTeam: String? = "",

    @Json(name = "bookmakers")
    var bookmakers: List<Bookmaker>? = null

) : BaseApiModel
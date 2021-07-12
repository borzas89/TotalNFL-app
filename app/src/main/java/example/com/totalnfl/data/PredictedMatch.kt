package example.com.totalnfl.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class PredictedMatch (

    @Json(name = "id")
    var id: Int,
    @Json(name = "homeTeam")
    var homeTeam: String = "",
    @Json(name = "awayTeam")
    var awayTeam: String = "",
    @Json( name ="homeScore")
    var homeScore: Double = 0.0,
    @Json( name ="awayScore")
    var awayScore : Double,
    @Json( name ="homeWinPercentage")
    var homeWinPercentage: Double = 0.0,
    @Json( name ="awayWinPercentage")
    var awayWinPercentage: Double = 0.0,
    @Json( name = "total")
    var total: Double = 0.0,
    @Json( name = "margin")
    var margin: Double = 0.0,
    @Json( name ="week")
    var week: Int,
    @Json( name ="matchDate")
    var matchDate: Date,
    @Json( name = "season")
    var season: String

): BaseApiModel
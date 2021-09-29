package example.com.totalnfl.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.lang.reflect.Constructor
import java.util.*

@JsonClass(generateAdapter = true)
data class PredictedMatch (

    @Json(name = "id")
    var id: Int = 0,
    @Json(name = "homeTeam")
    var homeTeam: String = "",
    @Json(name = "awayTeam")
    var awayTeam: String = "",
    @Json( name ="homeScore")
    var homeScore: Double? = 0.0,
    @Json( name ="awayScore")
    var awayScore : Double? = 0.0,
    @Json( name ="homeWinPercentage")
    var homeWinPercentage: Double? = 0.0,
    @Json( name ="awayWinPercentage")
    var awayWinPercentage: Double? = 0.0,
    @Json( name = "total")
    var total: Double? = 0.0,
    @Json( name = "margin")
    var margin: Double? = 0.0,
    @Json( name ="week")
    var week: Int = 0,
    @Json( name ="matchDate")
    var matchDate: Date = Date(),
    @Json( name = "season")
    var season: String = ""

): BaseApiModel {

    constructor(): this(0)

}



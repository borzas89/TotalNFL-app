package example.com.totalnfl.data.api


import com.google.gson.annotations.SerializedName
import java.util.*

data class PredictedMatch (

    @SerializedName( "id")
    var id: Int = 0,
    @SerializedName("homeTeam")
    var homeTeam: String = "",
    @SerializedName( "awayTeam")
    var awayTeam: String = "",
    @SerializedName( "homeScore")
    var homeScore: Double? = 0.0,
    @SerializedName( "awayScore")
    var awayScore : Double? = 0.0,
    @SerializedName("homeWinPercentage")
    var homeWinPercentage: Double? = 0.0,
    @SerializedName( "awayWinPercentage")
    var awayWinPercentage: Double? = 0.0,
    @SerializedName(  "total")
    var total: Double? = 0.0,
    @SerializedName(  "margin")
    var margin: Double? = 0.0,
    @SerializedName( "week")
    var week: Int = 0,
    @SerializedName( "matchDate")
    var matchDate: Date = Date(),
    @SerializedName( "season")
    var season: String = ""

): BaseApiModel {

    constructor(): this(0)

}



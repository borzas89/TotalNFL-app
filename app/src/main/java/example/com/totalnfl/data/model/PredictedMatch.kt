package example.com.totalnfl.data.model

import example.com.totalnfl.data.base.BaseModel
import java.util.*

data class PredictedMatch(
    var id: Int = 0,
    var homeTeam: String = "",
    var awayTeam: String = "",
    var homeScore: Double? = 0.0,
    var awayScore : Double? = 0.0,
    var homeWinPercentage: Double? = 0.0,
    var awayWinPercentage: Double? = 0.0,
    var total: Double? = 0.0,
    var margin: Double? = 0.0,
    var matchDate: Date = Date(),
): BaseModel
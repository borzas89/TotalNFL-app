package example.com.totalnfl.data.api

import com.google.gson.annotations.SerializedName
import example.com.totalnfl.data.base.BaseApiModel

data class MarketDto(
    @SerializedName( "commonMatchId") val commonMatchId: String = "",
    @SerializedName( "week") val week: Int = 0,
    @SerializedName( "awayTeam") val awayTeam: String = "",
    @SerializedName( "homeTeam") val homeTeam: String = "",
    @SerializedName( "marketTotal") val marketTotal: Double = 0.0,
    @SerializedName( "marketAwaySpread") val marketAwaySpread: Double = 0.0,
    @SerializedName( "marketHomeSpread") val marketHomeSpread: Double = 0.0,
) : BaseApiModel {
    constructor(): this("")
}

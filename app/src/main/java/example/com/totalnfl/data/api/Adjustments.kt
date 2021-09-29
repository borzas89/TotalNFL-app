package example.com.totalnfl.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Adjustments(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "team") val team: String = "",
    @Json(name = "games") val games: Int = 0,
    @Json(name = "raw_pf") val raw_pf: Double = 0.0,
    @Json(name = "raw_pa") val raw_pa: Int = 0,
    @Json(name = "rank_raw_pf") val rank_raw_pf: Int = 0,
    @Json(name = "rank_raw_pa") val rank_raw_pa: Int = 0,
    @Json(name = "adj_pf") val adj_pf: Double? = 0.0,
    @Json(name = "adj_pa") val adj_pa: Double? = 0.0,
    @Json(name = "pf_change") val pf_change: Double = 0.0,
    @Json(name = "pa_change") val pa_change: Double = 0.0,
    @Json(name = "adj_pf_rank") val adj_pf_rank: Int = 0,
    @Json(name = "adj_pa_rank") val adj_pa_rank: Int = 0,
    @Json(name = "pf_rank_change") val pf_rank_change: Int = 0,
    @Json(name = "pa_rank_change") val pa_rank_change: Int = 0,
    @Json(name = "sos_pa") val sos_pa: Int = 0,
    @Json(name = "sos_pf") val sos_pf: Int = 0,
    @Json(name = "pyth") val pyth: Double = 0.0
) : BaseApiModel {
    constructor(): this(0)
}
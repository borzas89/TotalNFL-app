package example.com.totalnfl.data.api

import com.google.gson.annotations.SerializedName


data class Adjustments(
    @SerializedName( "id") val id: Int = 0,
    @SerializedName( "team") val team: String = "",
    @SerializedName( "games") val games: Int = 0,
    @SerializedName( "raw_pf") val raw_pf: Double = 0.0,
    @SerializedName( "raw_pa") val raw_pa: Int = 0,
    @SerializedName( "rank_raw_pf") val rank_raw_pf: Int = 0,
    @SerializedName( "rank_raw_pa") val rank_raw_pa: Int = 0,
    @SerializedName("adj_pf") val adj_pf: Double? = 0.0,
    @SerializedName( "adj_pa") val adj_pa: Double? = 0.0,
    @SerializedName( "pf_change") val pf_change: Double = 0.0,
    @SerializedName( "pa_change") val pa_change: Double = 0.0,
    @SerializedName( "adj_pf_rank") val adj_pf_rank: Int = 0,
    @SerializedName( "adj_pa_rank") val adj_pa_rank: Int = 0,
    @SerializedName( "pf_rank_change") val pf_rank_change: Int = 0,
    @SerializedName( "pa_rank_change") val pa_rank_change: Int = 0,
    @SerializedName( "sos_pa") val sos_pa: Int = 0,
    @SerializedName( "sos_pf") val sos_pf: Int = 0,
    @SerializedName( "pyth") val pyth: Double = 0.0
) : BaseApiModel {
    constructor(): this(0)
}
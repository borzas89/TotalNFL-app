package example.com.totalnfl.data.model

import example.com.totalnfl.data.base.BaseModel

data class Market(
    override val id: Int = 0,
    val commonMatchId: String = "",
    val week: Int = 0,
    val awayName : String = "",
    val homeName: String = "",
    val marketAwaySpread: Double = 0.0,
    val marketHomeSpread: Double = 0.0,
    val marketTotal: Double = 0.0
): BaseModel
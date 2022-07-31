package example.com.totalnfl.data.model

import example.com.totalnfl.data.base.BaseModel

data class Adjustment(
     val id: Int = 0,
     val team: String = "",
     val pointsFor: Double = 0.0,
     val pointsAllowed: Double = 0.0,
     val sos_pa: Int = 0,
     val sos_pf: Int = 0
): BaseModel
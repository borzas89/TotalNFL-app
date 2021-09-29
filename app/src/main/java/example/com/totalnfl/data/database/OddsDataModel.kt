package example.com.totalnfl.data.database

import androidx.room.Entity
import java.util.*


@Entity(
    tableName = "Odds"
)
data class OddsDataModel (

    val awayTeam: String,
    val homeTeam: String,
    val eventDate : Date,
    val spreads: Double,
    val awaySpreadsPrice: Double,
    val homeSpreadsPrice: Double,
    val totalsLine: Double,
    val priceOver: Double,
    val priceUnder: Double

) : BaseDataModel()

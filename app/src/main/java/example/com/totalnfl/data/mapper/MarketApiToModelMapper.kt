package example.com.totalnfl.data.mapper

import example.com.totalnfl.data.api.MarketDto
import example.com.totalnfl.data.base.ApiModelToModelMapper
import example.com.totalnfl.data.model.Market
import javax.inject.Inject

class MarketApiToModelMapper @Inject constructor() :
    ApiModelToModelMapper<MarketDto, Market>() {

    override fun map(model: MarketDto) =
        Market(
            id = model.id,
            commonMatchId = model.commonMatchId,
            week = model.week,
            awayName = model.awayTeam,
            homeName = model.homeTeam,
            marketAwaySpread = model.marketAwaySpread,
            marketHomeSpread = model.marketHomeSpread,
            marketTotal = model.marketTotal
        )
}
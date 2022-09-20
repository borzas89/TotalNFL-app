package example.com.totalnfl.data.remote

import example.com.totalnfl.data.mapper.MarketApiToModelMapper
import example.com.totalnfl.data.model.Market
import example.com.totalnfl.network.TotalNflApi
import io.reactivex.Single
import javax.inject.Inject

class MarketServiceImpl
@Inject constructor(
    private var totalNflService: TotalNflApi,
    private var mapper: MarketApiToModelMapper
): MarketService {

    override fun getMarketLines(matchId: String): Single<Market> {
        return totalNflService.getMarketByCommonMatchId(matchId).map { market ->
            mapper.map(market)
        }
    }
}
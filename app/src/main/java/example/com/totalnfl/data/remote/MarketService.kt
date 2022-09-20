package example.com.totalnfl.data.remote

import example.com.totalnfl.data.model.Market
import io.reactivex.Single

interface MarketService {
    fun getMarketLines(matchId: String): Single<Market>
}
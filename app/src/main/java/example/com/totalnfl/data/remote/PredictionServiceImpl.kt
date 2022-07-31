package example.com.totalnfl.data.remote

import example.com.totalnfl.data.mapper.PredictedMatchApiToModelMapper
import example.com.totalnfl.data.model.PredictedMatch
import example.com.totalnfl.network.TotalNflApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PredictionServiceImpl
    @Inject constructor(
        private var totalNflService: TotalNflApi,
        private val mapper: PredictedMatchApiToModelMapper
): PredictionService{

    override fun gettingMatchesByDay(matchString: String): Single<List<PredictedMatch>> {
        return totalNflService.getPredictedMatchesByDay(matchString).map { items ->
            mapper.map(items)
        }.subscribeOn(Schedulers.io())
    }

    override fun getPredictedMatchById(matchId: Long): Single<PredictedMatch> {
        TODO("Not yet implemented")
    }

}

package example.com.totalnfl.data.remote

import example.com.totalnfl.data.model.PredictedMatch
import io.reactivex.Single

interface PredictionService {
    fun gettingMatchesByDay(matchString: String): Single<List<PredictedMatch>>
    fun getPredictedMatchById(matchId: Long): Single<PredictedMatch>
}
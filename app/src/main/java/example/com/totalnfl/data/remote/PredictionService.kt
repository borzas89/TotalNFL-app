package example.com.totalnfl.data.remote

import example.com.totalnfl.data.model.PredictedMatch
import io.reactivex.Observable
import io.reactivex.Single

interface PredictionService {
    fun gettingMatchesByDay(matchString: String): Single<List<PredictedMatch>>
    fun getPredictedMatchById(matchId: String): Single<PredictedMatch>
    fun getMatchById(matchId: String): Observable<PredictedMatch>
}
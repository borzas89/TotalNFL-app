package example.com.totalnfl.data.remote

import example.com.totalnfl.data.model.Adjustment
import io.reactivex.Single

interface AdjustmentService {
    fun getAdjustmentByTeamName(name: String): Single<Adjustment>
}
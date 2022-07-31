package example.com.totalnfl.data.remote

import example.com.totalnfl.data.mapper.AdjustmentApiToModelMapper
import example.com.totalnfl.data.model.Adjustment
import example.com.totalnfl.network.TotalNflApi
import io.reactivex.Single
import javax.inject.Inject

class AdjustmentServiceImpl
    @Inject constructor(
        private var totalNflService: TotalNflApi,
        private var mapper: AdjustmentApiToModelMapper
): AdjustmentService {

    override fun getAdjustmentByTeamName(name: String): Single<Adjustment> {
        return totalNflService.getAdjustmentsByTeamName(name).map { adjustments ->
            mapper.map(adjustments)
        }
    }
}
package example.com.totalnfl.data.mapper

import example.com.totalnfl.data.api.AdjustmentDto
import example.com.totalnfl.data.base.ApiModelToModelMapper
import example.com.totalnfl.data.model.Adjustment
import javax.inject.Inject

class AdjustmentApiToModelMapper @Inject constructor() :
    ApiModelToModelMapper<AdjustmentDto, Adjustment>() {

    override fun map(model: AdjustmentDto) =
        Adjustment(
        id = model.id,
        team = model.team,
        pointsAllowed = model.adj_pa?: 0.0,
        pointsFor = model.adj_pf?: 0.0
    )
}
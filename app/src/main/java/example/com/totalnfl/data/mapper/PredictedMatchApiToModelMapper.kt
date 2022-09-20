package example.com.totalnfl.data.mapper

import example.com.totalnfl.data.api.PredictedMatchDto
import example.com.totalnfl.data.base.ApiModelToModelMapper
import example.com.totalnfl.data.model.PredictedMatch
import javax.inject.Inject

class PredictedMatchApiToModelMapper @Inject constructor() :
    ApiModelToModelMapper<PredictedMatchDto, PredictedMatch>() {

    override fun map(model: PredictedMatchDto) = PredictedMatch(
        id = model.id,
        commonMatchId = model.commonMatchId,
        awayTeam = model.awayTeam,
        homeTeam = model.homeTeam,
        awayScore = model.awayScore,
        homeScore = model.homeScore,
        margin = model.margin,
        total = model.total,
        awayWinPercentage = model.awayWinPercentage,
        homeWinPercentage = model.homeWinPercentage,
        matchDate = model.matchDate
    )
}
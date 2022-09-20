package example.com.totalnfl.ui.detail

import android.util.Log
import androidx.databinding.ObservableField
import com.jakewharton.rxrelay2.BehaviorRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import example.com.totalnfl.arch.BaseViewModel
import example.com.totalnfl.data.model.Adjustment
import example.com.totalnfl.data.model.Market
import example.com.totalnfl.data.model.PredictedMatch
import example.com.totalnfl.data.remote.AdjustmentService
import example.com.totalnfl.data.remote.MarketService
import example.com.totalnfl.data.remote.PredictionService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailBottomSheetViewModel @Inject constructor(
    private var predictionService: PredictionService,
    private var adjustmentService: AdjustmentService,
    private var marketService: MarketService
) : BaseViewModel() {

    val predictedMatch: BehaviorRelay<PredictedMatch> = BehaviorRelay.createDefault(
        PredictedMatch()
    )
    val awayAdjustments: BehaviorRelay<Adjustment> = BehaviorRelay.createDefault(
        Adjustment()
    )
    val homeAdjustments: BehaviorRelay<Adjustment> = BehaviorRelay.createDefault(
        Adjustment()
    )
    val prediction = ObservableField<PredictedMatch>()
    val awayAdjustment = ObservableField<Adjustment>()
    val homeAdjustment = ObservableField<Adjustment>()

    val marketDatas: BehaviorRelay<Market> = BehaviorRelay.createDefault(
        Market()
    )
    val marketData = ObservableField<Market>()

    val suggestion = ObservableField<String>()
    val matchTitle = ObservableField<String>()

    fun gettingDetailData(id: Long) {
        predictionService.getPredictedMatchById(id.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .compose(applySingleTransformers())
            .subscribeBy(
                onSuccess = { result ->
                    when (result) {
                        result -> predictedMatch.accept(result)

                    }
                    matchTitle.set(predictedMatch.value.awayTeam + " @ " + predictedMatch.value.homeTeam)
                    gettingMarketData(predictedMatch.value.commonMatchId)
                    calculateSuggestion()
                }, onError = {
                    Log.d("TOTAL_NFL_API", it.message.toString())
                }
            ).addTo(compositeDisposable)
    }

    fun gettingAwayAdjustmentsData(name: String) {
        adjustmentService.getAdjustmentByTeamName(name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .compose(applySingleTransformers())
            .subscribeBy(
                onSuccess = { result ->
                    when (result) {
                        result -> awayAdjustments.accept(result)

                    }
                }, onError = {
                    Log.d("TOTAL_NFL_API", it.message.toString())
                }
            ).addTo(compositeDisposable)

    }

    fun gettingHomeAdjustmentsData(name: String) {
        adjustmentService.getAdjustmentByTeamName(name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .compose(applySingleTransformers())
            .subscribeBy(
                onSuccess = { result ->
                    when (result) {
                        result -> homeAdjustments.accept(result)

                    }
                }, onError = {
                    Log.d("TOTAL_NFL_API", it.message.toString())
                }
            ).addTo(compositeDisposable)
    }

    fun gettingMarketData(matchId: String) {
        marketService.getMarketLines(matchId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .compose(applySingleTransformers())
            .subscribeBy(
                onSuccess = { result ->
                    when (result) {
                        result -> marketData.set(result)

                    }
                    calculateSuggestion()
                }, onError = {
                    Log.d("TOTAL_NFL_API", it.message.toString())
                }
            ).addTo(compositeDisposable)
    }

    private fun calculateSuggestion() {
        val predictedTotal = predictedMatch.value?.total
        val marketTotal = marketData.get()?.marketTotal

        if (predictedTotal!! > marketTotal!!) {
            suggestion.set("Suggestion is: take OVER")
        } else {
            suggestion.set("Suggestion is: take UNDER")
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}
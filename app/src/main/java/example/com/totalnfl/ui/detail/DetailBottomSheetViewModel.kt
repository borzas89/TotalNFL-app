package example.com.totalnfl.ui.detail

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class DetailBottomSheetViewModel @Inject constructor(
    private var predictionService: PredictionService,
    private var adjustmentService: AdjustmentService,
    private var marketService: MarketService
) : BaseViewModel() {
    private val _predictedMatch = MutableLiveData<PredictedMatch>()
    val prediction: LiveData<PredictedMatch> = _predictedMatch

    private val _awayAdjustment = MutableLiveData<Adjustment>()
    val awayAdjustment: LiveData<Adjustment> = _awayAdjustment

    private val _homeAdjustment = MutableLiveData<Adjustment>()
    val homeAdjustment: LiveData<Adjustment> = _homeAdjustment

    private val _marketData = MutableLiveData<Market>()
    val market: LiveData<Market> = _marketData

    val filterId: BehaviorSubject<Long> = BehaviorSubject.createDefault(0L)

    val suggestion = ObservableField<String>()

    init {
        filterId.subscribe {
            gettingDetailData()
        }.addTo(compositeDisposable)
    }

    fun gettingDetailData() {
        filterId.value?.let { id ->
            predictionService
                .getPredictedMatchById(id.toString())
                .compose(applySingleTransformers())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                    onSuccess = { result ->
                        _predictedMatch.value = result
                        prediction.value?.let {
                            gettingMarketData(it.commonMatchId)
                        }
                        calculateSuggestion()
                    }, onError = {
                        Log.d("TOTAL_NFL_API_DETAIL", it.message.toString())
                    }
                ).addTo(compositeDisposable)
        }

    }

    fun gettingAwayAdjustmentsData(name: String) {
        adjustmentService.getAdjustmentByTeamName(name)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .compose(applySingleTransformers())
            .subscribeBy(
                onSuccess = { result ->
                    when (result) {
                        result -> _awayAdjustment.value = result
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
                        result -> _homeAdjustment.value = result
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
                        result -> _marketData.value = result
                    }
                    calculateSuggestion()
                }, onError = {
                    Log.d("TOTAL_NFL_API_MARKET", it.message.toString())
                }
            ).addTo(compositeDisposable)
    }

    private fun calculateSuggestion() {
        val predictedTotal = prediction.value?.total
        market.value?.let {
            if (predictedTotal!! > it.marketTotal) {
                suggestion.set("Suggestion is: take OVER")
            } else {
                suggestion.set("Suggestion is: take UNDER")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
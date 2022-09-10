package example.com.totalnfl.ui.detail

import android.util.Log
import androidx.databinding.ObservableField
import com.jakewharton.rxrelay2.BehaviorRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import example.com.totalnfl.arch.BaseViewModel
import example.com.totalnfl.data.api.AdjustmentDto
import example.com.totalnfl.data.api.PredictedMatchDto
import example.com.totalnfl.network.TotalNflApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private var totalNflService: TotalNflApi,
) : BaseViewModel() {

    val predictedMatch: BehaviorRelay<PredictedMatchDto> = BehaviorRelay.createDefault(
        PredictedMatchDto())
    val awayAdjustments: BehaviorRelay<AdjustmentDto> = BehaviorRelay.createDefault(
        AdjustmentDto())
    val homeAdjustments: BehaviorRelay<AdjustmentDto> = BehaviorRelay.createDefault(
        AdjustmentDto())
    val prediction = ObservableField<PredictedMatchDto>()
    val awayAdjustment = ObservableField<AdjustmentDto>()
    val homeAdjustment = ObservableField<AdjustmentDto>()

    fun gettingDetailData(id: Long) {
        totalNflService.getPredictedMatchById(id.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .compose(applySingleTransformers())
            .subscribeBy(
                onSuccess = { result ->
                    when (result) {
                        result -> predictedMatch.accept(result)

                    }
                }, onError = {
                    Log.d("TOTAL_NFL_API", it.message.toString())
                }
            ).addTo(compositeDisposable)

    }

    fun gettingAwayAdjustmentsData(name: String) {
        totalNflService.getAdjustmentsByTeamName(name).observeOn(AndroidSchedulers.mainThread())
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
        totalNflService.getAdjustmentsByTeamName(name).observeOn(AndroidSchedulers.mainThread())
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}
package example.com.totalnfl.ui.detail

import android.util.Log
import androidx.databinding.ObservableField
import com.jakewharton.rxrelay2.BehaviorRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import example.com.totalnfl.arch.BaseViewModel
import example.com.totalnfl.data.api.Adjustments
import example.com.totalnfl.data.api.PredictedMatch
import example.com.totalnfl.network.TotalNflService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private var totalNflService: TotalNflService,
) : BaseViewModel() {

    val predictedMatch: BehaviorRelay<PredictedMatch> = BehaviorRelay.createDefault(
        PredictedMatch())
    val awayAdjustments: BehaviorRelay<Adjustments> = BehaviorRelay.createDefault(
        Adjustments())
    val homeAdjustments: BehaviorRelay<Adjustments> = BehaviorRelay.createDefault(
        Adjustments())
    val prediction = ObservableField<PredictedMatch>()
    val awayAdjustment = ObservableField<Adjustments>()
    val homeAdjustment = ObservableField<Adjustments>()

    private val bag = CompositeDisposable()

    fun gettingDetailData(id: Long) {
        totalNflService.getPredictedMatchById(id.toString()).observeOn(AndroidSchedulers.mainThread())
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
            )

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
            )

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
            )

    }

    override fun onCleared() {
        super.onCleared()
        bag.clear()
    }

}
package example.com.totalnfl.ui.list

import androidx.databinding.ObservableField
import com.jakewharton.rxrelay2.BehaviorRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import example.com.totalnfl.arch.BaseViewModel
import example.com.totalnfl.data.api.PredictedMatch
import example.com.totalnfl.network.TotalNflService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private var totalNflService: TotalNflService
) : BaseViewModel() {
    val predictions = BehaviorRelay.createDefault(listOf<PredictedMatch>())
    val filterDay: BehaviorSubject<String> = BehaviorSubject.createDefault(
        formattedToday()
    )
    val errorTitle = ObservableField("No events today")

    private fun formattedToday(): String {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
        return formatter.format(LocalDate.now().atStartOfDay())
    }

    init {
        filterDay.subscribe {
            gettingMatchesByDay()
        }.addTo(compositeDisposable)
    }

    private fun refreshStates() {
        predictions.subscribe {
            if (it.isEmpty()) {
                listViewVisible.set(false)
                emptyViewVisible.set(true)
                errorTitle.set("No events today")
            } else {
                listViewVisible.set(true)
                emptyViewVisible.set(false)
            }
        }.addTo(compositeDisposable)
    }

    private fun gettingMatchesByDay() {
        totalNflService.getPredictedMatchesByDay(
            filterDay.value!!.toString()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .compose(applyObserveTransformers())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onNext = { result ->
                    when (result) {
                        result -> predictions.accept(result.sortedBy { predictedMatch ->
                            predictedMatch.matchDate
                         }
                        )
                    }
                }, onError = {
                    Timber.d("Error: ${it.message.toString()}")
                    errorTitle.set("Something went wrong, try again later...")
                },
                onComplete = {
                    refreshStates()
                }
            ).addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
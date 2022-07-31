package example.com.totalnfl.ui.list

import androidx.databinding.ObservableField
import com.jakewharton.rxrelay2.BehaviorRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import example.com.totalnfl.arch.BaseViewModel
import example.com.totalnfl.data.model.PredictedMatch
import example.com.totalnfl.data.remote.PredictionService
import example.com.totalnfl.util.formattedToday
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val predictionService: PredictionService
) : BaseViewModel() {
    val predictions = BehaviorRelay.createDefault(listOf<PredictedMatch>())
    val filterDay: BehaviorSubject<String> = BehaviorSubject.createDefault(
        formattedToday()
    )
    val errorTitle = ObservableField("No events today")

    init {
        filterDay.subscribe { filteredDay ->
            predictionService
                .gettingMatchesByDay(filteredDay)
                .compose(applySingleTransformers())
                .subscribeBy(
                    onSuccess = { result ->
                        when (result) {
                            result -> predictions.accept(result.sortedBy { predictedMatch ->
                                predictedMatch.matchDate
                            })
                        }
                        refreshStates()
                    }, onError = {
                        Timber.d("Error: ${it.message.toString()}")
                        errorTitle.set("Something went wrong, try again later...")
                    },
                )
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

    fun filterToday(){
        val format1 = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
        val formatter = format1.format(LocalDate.now().atStartOfDay())
        filterDay.onNext(formatter)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
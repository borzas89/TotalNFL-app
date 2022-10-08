package example.com.totalnfl.ui.list

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import example.com.totalnfl.arch.BaseViewModel
import example.com.totalnfl.data.model.PredictedMatch
import example.com.totalnfl.data.remote.PredictionService
import example.com.totalnfl.arch.Event
import example.com.totalnfl.util.formattedToday
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
    private val predictionService: PredictionService
) : BaseViewModel(), PredictionListener {
    private val _predictionList = MutableLiveData<List<PredictedMatch>>()
    val predictionList: LiveData<List<PredictedMatch>> = _predictionList

    val errorTitle = ObservableField("No events today")
    val filterDay: BehaviorSubject<String> = BehaviorSubject.createDefault(
        formattedToday()
    )

    val showDetail = MutableLiveData<Event<PredictedMatch>>()

    init {
        filterDay.subscribe {
            gettingMatchesByDay()
        }.addTo(compositeDisposable)
    }

    private fun gettingMatchesByDay() {
        errorTitle.set("")
        filterDay.value?.let { filteredDay ->
            predictionService
                .gettingMatchesByDay(filteredDay)
                .compose(applySingleTransformers())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                    onSuccess = { result ->
                        _predictionList.value = result
                        refreshStates()
                    },
                    onError = {
                        Timber.d("Error: ${it.message.toString()}")
                        errorTitle.set("Something went wrong, try again later...")
                    }
                )
                .addTo(compositeDisposable)
        }
    }

    private fun refreshStates() {
        predictionList.value?.let { list ->
            if (list.isEmpty()) {
                listViewVisible.set(false)
                emptyViewVisible.set(true)
                errorTitle.set("No events today")
            } else {
                listViewVisible.set(true)
                emptyViewVisible.set(false)
            }
        }
    }

    fun filterToday() {
        val format = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
        val formatter = format.format(LocalDate.now().atStartOfDay())
        filterDay.onNext(formatter)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    override fun onPredictionClicked(prediction: PredictedMatch) {
        showDetail.value = Event(prediction)
    }
}
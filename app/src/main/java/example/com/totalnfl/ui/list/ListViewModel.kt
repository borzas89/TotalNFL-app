package example.com.totalnfl.ui.list

import android.util.Log
import androidx.databinding.ObservableField
import com.jakewharton.rxrelay2.BehaviorRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import example.com.totalnfl.arch.BaseViewModel
import example.com.totalnfl.data.api.PredictedMatch
import example.com.totalnfl.network.TotalNflService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.flow.StateFlow
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private var totalNflService: TotalNflService
): BaseViewModel() {
    val predictions = BehaviorRelay.createDefault(listOf<PredictedMatch>())
    val filterDay: BehaviorSubject<String> = BehaviorSubject.createDefault(
        formattedToday())
    val errorTitle = ObservableField<String>()
    val listViewVisible = ObservableField<Boolean>()
    val emptyViewVisible = ObservableField<Boolean>()

    fun formattedToday(): String {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
        return formatter.format(LocalDate.now().atStartOfDay())
    }

    init {
        filterDay.subscribe {
            gettingMatchesByDay()
        }.addTo(compositeDisposable)
    }

    private fun gettingMatchesByDay(){
        listViewVisible.set(true)
        emptyViewVisible.set(false)
        totalNflService.getPredictedMatchesByDay(
            filterDay.value!!.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(applySingleTransformers())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { result ->
                    when (result) {
                        result -> predictions.accept(result.sortedBy {
                                predictedMatch -> predictedMatch.matchDate  }

                        )
                    }

                }, onError = {
                    Log.d("Error",it.message.toString())
                    errorTitle.set(it.message.toString())
                    listViewVisible.set(false)
                    emptyViewVisible.set(true)
                }
            ).addTo(compositeDisposable)

        if(predictions.value.isEmpty()){
            errorTitle.set("No events today")
        } else{
            errorTitle.set("")
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
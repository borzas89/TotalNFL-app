package example.com.totalnfl.ui.list

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import example.com.totalnfl.arch.BaseViewModel
import example.com.totalnfl.data.PredictedMatch
import example.com.totalnfl.network.OddsApiService
import example.com.totalnfl.network.TotalNflService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private var totalNflService: TotalNflService
): BaseViewModel() {
    val filterWeek :  BehaviorSubject<Int> = BehaviorSubject.createDefault(1)
    val predictions = BehaviorRelay.createDefault(listOf<PredictedMatch>())

    private val bag = CompositeDisposable()

    fun getWeeksList(): List<String> {
        return listOf("Week 1", "Week 2", "Week 3", "Week 4", "Week 5",
            "Week 6", "Week 7", "Week 8", "Week 9", "Week 10",
                "Week 11", "Week 12", "Week 13", "Week 14", "Week 15",
                "Week 16", "Week 17", "Week 18")
    }

    init {
        filterWeek.subscribe {
           gettingPreMatchesByWeek()
        }.addTo(bag)
    }


    fun filterMatches(){
        totalNflService.getPredictedRegMatches().observeOn(AndroidSchedulers.mainThread())
            .compose(applySingleTransformers())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { result ->
                    when (result) {
                        result -> predictions.accept(result.filter {
                            it.week == filterWeek.value })
                    }

                }, onError = {

                }
            )

    }

    fun gettingPreMatchesByWeek(){
        totalNflService.getPredictedRegMatchesByWeek(
            filterWeek.value.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(applySingleTransformers())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { result ->
                    when (result) {
                        result -> predictions.accept(result)
                    }

                }, onError = {
                    Log.d("Error",it.message.toString())
                }
            )

    }

    override fun onCleared() {
        super.onCleared()
        bag.clear()

    }


}
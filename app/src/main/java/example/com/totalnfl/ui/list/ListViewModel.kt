package example.com.totalnfl.ui.list

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import example.com.totalnfl.arch.BaseViewModel
import example.com.totalnfl.data.PredictedMatch
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
    private var api: TotalNflService
): BaseViewModel() {
    val filterWeek :  BehaviorSubject<Int> = BehaviorSubject.createDefault(1)
    val predictions = BehaviorRelay.createDefault(listOf<PredictedMatch>())
    private val bag = CompositeDisposable()

    fun getWeeksList(): List<String> {
        return listOf("Week 1", "Week 2", "Week 3", "Week 4", "Week 5",
            "Week 6")
    }

    init {
        filterWeek.subscribe {
           filterMatches()
        }.addTo(bag)
    }

    fun filterMatches(){
        api.getPredictedRegMatches().observeOn(AndroidSchedulers.mainThread())
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

    override fun onCleared() {
        super.onCleared()
        bag.clear()

    }


}
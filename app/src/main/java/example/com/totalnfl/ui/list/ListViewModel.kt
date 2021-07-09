package example.com.totalnfl.ui.list

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import example.com.totalnfl.arch.BaseViewModel
import example.com.totalnfl.data.PredictedMatch
import example.com.totalnfl.network.TotalNflService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    api: TotalNflService
): BaseViewModel() {
    val predictions = BehaviorRelay.createDefault(listOf<PredictedMatch>())
    private val bag = CompositeDisposable()

    init {
        api.getPredictedRegMatches().observeOn(AndroidSchedulers.mainThread())
            .compose(applySingleTransformers())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { result ->
                    when (result) {
                        result -> predictions.accept(result.filter { it.week == 1 })
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
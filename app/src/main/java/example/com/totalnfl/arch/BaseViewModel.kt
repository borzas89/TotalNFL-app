package example.com.totalnfl.arch

import androidx.annotation.CallSuper
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import example.com.totalnfl.util.wrap
import io.reactivex.Observable
import io.reactivex.SingleTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.BehaviorProcessor

open class BaseViewModel  : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    private val loadingProcessor = BehaviorProcessor.create<Boolean>()

    val isLoading = loadingProcessor.value ?: false

    val isLoadingProgress = ObservableField<Boolean>()

    val isLoadingFlowable
        get() = loadingProcessor.wrap()

    @CallSuper
    open fun showLoader() = loadingProcessor.onNext(true )

    @CallSuper
    open fun stopLoader() = loadingProcessor.onNext(false)


    protected fun <U> applySingleTransformers(): SingleTransformer<U, U> {
        return SingleTransformer { upstream ->
            upstream
                    .doOnSubscribe {
                        isLoadingProgress.set(true)
                        showLoader() }
                    .doFinally {
                        isLoadingProgress.set(false)
                        stopLoader() }
        }
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
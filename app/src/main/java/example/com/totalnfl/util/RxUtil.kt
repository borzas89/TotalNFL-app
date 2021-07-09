package example.com.totalnfl.util

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.FlowableProcessor

fun <T> FlowableProcessor<T>.wrap(): Flowable<T> = Flowable.create({ source ->
    val disposable = subscribe(source::onNext, source::onError)

    source.setCancellable(disposable::dispose)
}, BackpressureStrategy.BUFFER)

fun Unit.safe() = Unit

fun Disposable.disposedBy(bag: CompositeDisposable) {
    bag.add(this)
}
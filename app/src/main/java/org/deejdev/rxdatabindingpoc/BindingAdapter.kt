package org.deejdev.rxdatabindingpoc

import android.databinding.BindingAdapter
import android.view.View
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

@BindingAdapter("rxText")
fun setRxText(view: TextView, rxText: Observable<String>) {
    view.cleanup()

    val disposable = rxText.subscribe { view.text = it }
    view.setTag(R.id.disposable, disposable)

    view.cleanupOnDetach(disposable)
}

private fun View.cleanupOnDetach(disposable: Disposable) {
    val detachListener = object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(view: View) = Unit
        override fun onViewDetachedFromWindow(view: View) = view.cleanup(this, disposable)
    }

    addOnAttachStateChangeListener(detachListener)
}

private fun View.cleanup(
        detachListener: View.OnAttachStateChangeListener? = getTag(R.id.detachListener) as? View.OnAttachStateChangeListener,
        disposable: Disposable? = getTag(R.id.disposable) as? Disposable
) {
    disposable?.dispose()
    removeOnAttachStateChangeListener(detachListener)
}

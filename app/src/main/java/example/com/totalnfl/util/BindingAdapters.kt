package example.com.totalnfl.util

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @JvmStatic
    @BindingAdapter(value = ["isVisibleOrGone"], requireAll = false)
    fun isVisibleOrGone(view: View, isVisible: Boolean?) {
        view.visibility = if (isVisible == true) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter(value = ["isVisibleOrInvisible"], requireAll = false)
    fun isVisibleOrInvisible(view: View, isVisible: Boolean?) {
        view.visibility = if (isVisible == true) View.VISIBLE else View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter(value = ["doubleFormat"], requireAll = false)
    fun bindDoubleValues(view: TextView, double: Double?) {
        if (double == null || double == 0.0) {
            view.text = "-"
        } else {
            view.text = double.toString()
        }
    }
}
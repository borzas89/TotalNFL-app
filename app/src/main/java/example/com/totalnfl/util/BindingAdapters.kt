package example.com.totalnfl.util

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import example.com.totalnfl.R

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
    @JvmStatic
    @BindingAdapter(value = ["eventFormat"], requireAll = false)
    fun bindTitleValues(view: TextView, awayTeam: String?, homeTeam: String?) {
        if (awayTeam == null || homeTeam == null) {
            view.text = "-"
        } else {
            view.text = view.context.getString(R.string.event_title, awayTeam, homeTeam)
        }
    }

    @SuppressLint("StringFormatMatches")
    @JvmStatic
    @BindingAdapter(value = ["percentFormat"], requireAll = false)
    fun bindPercentValues(view: TextView, percent: Double?) {
        if (percent == null) {
            view.text = "-"
        } else {
            view.text = view.context.getString(R.string.formatPercent, percent)
        }
    }
}
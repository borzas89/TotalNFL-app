package example.com.totalnfl.util

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import example.com.totalnfl.R
import example.com.totalnfl.arch.BaseAdapter
import example.com.totalnfl.data.base.BaseModel
import example.com.totalnfl.ui.view.WinProgressView

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
    @BindingAdapter(value = ["roundFormat"], requireAll = false)
    fun bindRoundValues(view: TextView, double: Double?) {
        if (double == null || double == 0.0) {
            view.text = "-"
        } else {
            view.text = rounding(double).toString()
        }
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

    @JvmStatic
    @BindingAdapter("setAdapter")
    fun setAdapter(
        recyclerView: RecyclerView,
        adapter: BaseAdapter<ViewDataBinding, BaseModel>?
    ) {
        adapter?.let {
            recyclerView.adapter = it
        }
    }

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    @BindingAdapter("submitList")
    fun submitList(recyclerView: RecyclerView, list: List<BaseModel>?) {
        val adapter = recyclerView.adapter as BaseAdapter<ViewDataBinding, BaseModel>?
        adapter?.updateData(list ?: listOf())
    }

    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(view: ImageView, imageName: String){
        view.setImageResource(imageResolverId(imageName))
    }

    @JvmStatic
    @BindingAdapter("setHomeProbability")
    fun setHomeProbability(view: WinProgressView, winHome: Double){
        view.winHomeFloat = winHome.toFloat()
    }

    @JvmStatic
    @BindingAdapter("setAwayProbability")
    fun setAwayProbability(view: WinProgressView, winAway: Double){
        view.winAwayFloat = winAway.toFloat()
    }

}
package example.com.totalnfl.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import example.com.totalnfl.R
import example.com.totalnfl.data.model.PredictedMatch
import example.com.totalnfl.databinding.ListItemBinding
import example.com.totalnfl.util.imageResolverId
import example.com.totalnfl.util.onClick
import example.com.totalnfl.util.rounding

typealias ItemClickedlambda = (v: View, position: Int) -> Unit
class PredictedMatchAdapter(var onItemClicked: ItemClickedlambda) :
    RecyclerView.Adapter<PredictedMatchAdapter.ViewHolder>() {

    var predictions: List<PredictedMatch> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(predictions[position])
    }

    override fun getItemCount(): Int = predictions.size

    fun updateData(data: List<PredictedMatch>) {
        this.predictions = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.listFrameItem.onClick {
                val adapterPosition = adapterPosition.takeIf { it >= 0 } ?: return@onClick
                onItemClicked.invoke(it, adapterPosition)
            }

        }

        @SuppressLint("ResourceAsColor")
        fun bind(item: PredictedMatch) {
            binding.imageAway.setImageResource(imageResolverId(item.awayTeam))
            binding.imageHome.setImageResource(imageResolverId(item.homeTeam))

            item.let {
                binding.awayScore.text = rounding(item.awayScore!!).toString()
                binding.homeScore.text = rounding(item.homeScore!!).toString()
            }

            binding.predictedMatchTitle.text =
                binding.root.context.getString(R.string.event_title, item.awayTeam, item.homeTeam)

            binding.predictedScore.text = rounding(item.total!!).toString()

            binding.winPercentage.winHomeFloat = item.homeWinPercentage!!.toFloat()
            binding.winPercentage.winAwayFloat = item.awayWinPercentage!!.toFloat()
        }

    }
}
package example.com.totalnfl.ui.list

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import example.com.totalnfl.data.PredictedMatch
import example.com.totalnfl.databinding.ListItemBinding
import example.com.totalnfl.util.imageResolverId
import example.com.totalnfl.util.onClick

typealias ItemClickedlambda = (v: View, position: Int) -> Unit

class PredictedMatchAdapter(var onItemClicked: ItemClickedlambda): RecyclerView.Adapter<PredictedMatchAdapter.ViewHolder>() {

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
           binding.infoLayout.onClick {
               val adapterPosition = adapterPosition.takeIf { it >= 0 } ?: return@onClick
               onItemClicked.invoke(it,adapterPosition)
           }

       }

       fun bind(item: PredictedMatch) {
           binding.imageAway.setImageResource(imageResolverId(item.awayTeam.toString()))
           binding.imageHome.setImageResource(imageResolverId(item.homeTeam.toString()))
           binding.awayScore.text = item.awayScore.toString()
           binding.homeScore.text = item.homeScore.toString()
           binding.predictedMatchTitle.text = item.awayTeam + " vs " + item.homeTeam
           binding.predictedScore.text = item.total.toString()
        }

   }




}
package example.com.totalnfl.ui.list

import example.com.totalnfl.R
import example.com.totalnfl.arch.BaseAdapter
import example.com.totalnfl.data.model.PredictedMatch
import example.com.totalnfl.databinding.ListItemBinding

class PredictionAdapter (
    private val list: List<PredictedMatch>,
    private val predictionListener: PredictionListener
) : BaseAdapter<ListItemBinding, PredictedMatch>(list) {

    override val layoutId: Int = R.layout.list_item

    override fun bind(binding: ListItemBinding, item: PredictedMatch) {
        binding.apply {
            prediction = item
            listener = predictionListener
            executePendingBindings()
        }
    }
}

interface PredictionListener {
    fun onPredictionClicked(prediction: PredictedMatch)
}
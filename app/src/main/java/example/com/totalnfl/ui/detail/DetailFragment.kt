package example.com.totalnfl.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import example.com.totalnfl.databinding.FragmentDetailBinding
import example.com.totalnfl.util.disposedBy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private val bag = CompositeDisposable()

    override fun onStart() {
        super.onStart()
        viewModel.gettingDetailData(args.predictionId)
        viewModel.gettingAwayAdjustmentsData(args.awayTeam)
        viewModel.gettingHomeAdjustmentsData(args.homeTeam)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        binding.run {
            lifecycleOwner = this@DetailFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.predictedMatch.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { prediction ->
                viewModel.prediction.set(prediction)
            }
            .disposedBy(bag)

        viewModel.awayAdjustments.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { adjustment ->
                viewModel.awayAdjustment.set(adjustment)
            }
            .disposedBy(bag)

        viewModel.homeAdjustments.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { adjustment ->
                viewModel.homeAdjustment.set(adjustment)
            }
            .disposedBy(bag)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        bag.clear()
    }

}

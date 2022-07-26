package example.com.totalnfl.ui.detail

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import example.com.totalnfl.databinding.BottomSheetDialogDetailBinding
import example.com.totalnfl.util.disposedBy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@AndroidEntryPoint
class DetailBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetDialogDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailBottomSheetViewModel by viewModels()
    private lateinit var dialog: BottomSheetDialog
    private val bag = CompositeDisposable()

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, example.com.totalnfl.R.style.AppBottomSheetDialogTheme)

    }


    override fun onStart() {
        super.onStart()

        val predictionId = requireArguments().getLong("id")
        val awayTeam = requireArguments().getString("awayName")
        val homeTeam = requireArguments().getString("homeName")

        viewModel.gettingDetailData(predictionId)
        viewModel.gettingAwayAdjustmentsData(awayTeam!!)
        viewModel.gettingHomeAdjustmentsData(homeTeam!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomSheetDialogDetailBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        binding.run {
            lifecycleOwner = this@DetailBottomSheetDialogFragment
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


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        dialog.setOnShowListener { dialogInterface ->
            binding.executePendingBindings()
            view?.requestLayout()

            val bottomSheet =
                dialog.findViewById<View>(example.com.totalnfl.R.id.design_bottom_sheet) as FrameLayout?

            BottomSheetBehavior.from(bottomSheet!!).setState(BottomSheetBehavior.STATE_EXPANDED)

            val bottomSheetInternal = bottomSheet.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheetInternal).peekHeight = Resources.getSystem().getDisplayMetrics().heightPixels

        }
        return dialog
    }

    companion object {
        @JvmStatic
        fun newInstance(
            Id: Long,
            homeTeamName: String,
            awayTeamName: String
        ): DetailBottomSheetDialogFragment {
            return DetailBottomSheetDialogFragment().apply {
                arguments = Bundle().also { bundle ->
                    bundle.putLong("id", Id)
                    bundle.putString("homeName", homeTeamName)
                    bundle.putString("awayName", awayTeamName)
                }
            }
        }
    }
}
package example.com.totalnfl.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import example.com.totalnfl.R
import example.com.totalnfl.databinding.FragmentListBinding
import example.com.totalnfl.ui.detail.DetailBottomSheetDialogFragment
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class ListFragment : Fragment(), OnDateSelectedListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListViewModel by viewModels()

    lateinit var adapter: PredictedMatchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = this@ListFragment.viewModel
            lifecycleOwner = this@ListFragment
        }
        binding.adapter = PredictionAdapter(listOf(), viewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendarViewSingle.setOnDateChangedListener(this)
        binding.calendarViewSingle.setDateSelected(CalendarDay.today(), true)

        viewModel.showDetail.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { prediction ->
                val id = prediction.id.toLong()
                val awayTeam = prediction.awayTeam
                val homeTeam = prediction.homeTeam
                val commonMatchId = prediction.commonMatchId
                openDetailDialog(id, homeTeam, awayTeam, commonMatchId)
            }
        }
    }

    private fun openDetailDialog(
        id: Long,
        homeName: String,
        awayName: String,
        commonMatchId: String
    ) {
        DetailBottomSheetDialogFragment.newInstance(id, homeName, awayName, commonMatchId)
            .show(this.requireFragmentManager(), "DetailBottomSheetDialog")
    }

    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean
    ) {
        val format = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
        val formatter = format.format(date.date.atStartOfDay())

        viewModel.filterDay.onNext(formatter)
    }

    private fun setCalendarToday() {
        binding.calendarViewSingle.selectedDate = CalendarDay.today()
        binding.calendarViewSingle.currentDate = CalendarDay.today()
        binding.calendarViewSingle.setDateSelected(CalendarDay.today(), true)
        viewModel.filterToday()
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                setCalendarToday()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
package example.com.totalnfl.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import example.com.totalnfl.R
import example.com.totalnfl.databinding.FragmentListBinding
import example.com.totalnfl.ui.detail.DetailBottomSheetDialogFragment
import example.com.totalnfl.util.disposedBy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class ListFragment : Fragment(), OnDateSelectedListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListViewModel by viewModels()
    private val bag = CompositeDisposable()

    lateinit var adapter: PredictedMatchAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    private fun loadData() {
        viewModel.predictions.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { list -> adapter.updateData(list) }
            .disposedBy(bag)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.predictionRecyclerView

        binding.calendarViewSingle.setOnDateChangedListener(this)
        binding.calendarViewSingle.setDateSelected(CalendarDay.today(),true)

        attachUI()

        loadData()
    }

    private fun attachUI() {
        val linearLayoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(dividerItemDecoration)

        initializeListView()
    }

    private fun initializeListView() {
        adapter = PredictedMatchAdapter() { view, position -> rowTapped(position) }
        recyclerView.adapter = adapter
    }

    private fun rowTapped(position: Int) {
        val id = adapter.predictions[position].id.toLong()
        val awayTeam = adapter.predictions[position].awayTeam
        val homeTeam = adapter.predictions[position].homeTeam

        openDetailDialog(id,homeTeam,awayTeam)
    }

    private fun openDetailDialog(id: Long, homeName: String, awayName: String) {
        DetailBottomSheetDialogFragment.newInstance(id,homeName,awayName)
            .show(this.requireFragmentManager(), "DetailBottomSheetDialog")
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        val format = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH)
        val formatter = format.format(date.date.atStartOfDay())

        viewModel.filterDay.onNext(formatter)
    }

    private fun setCalendarToday(){
        binding.calendarViewSingle.selectedDate = CalendarDay.today()
        binding.calendarViewSingle.currentDate = CalendarDay.today()
        binding.calendarViewSingle.setDateSelected(CalendarDay.today(),true)
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

    override fun onDestroyView() {
        super.onDestroyView()
        bag.clear()
    }
}
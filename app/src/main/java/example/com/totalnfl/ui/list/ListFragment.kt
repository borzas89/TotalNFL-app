package example.com.totalnfl.ui.list

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import example.com.totalnfl.databinding.FragmentListBinding
import example.com.totalnfl.navigator.AppNavigator
import example.com.totalnfl.util.disposedBy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListViewModel by viewModels()
    private val bag = CompositeDisposable()

    lateinit var adapter: PredictedMatchAdapter
    lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var navigator: AppNavigator

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

        attachUI()

        loadData()

        creatingWeekSelector()

    }

    private fun creatingWeekSelector() {
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(), R.layout.simple_spinner_item, viewModel.getWeeksList()
        )
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.weekSpinner.adapter = arrayAdapter
        binding.weekSpinner.setSelection(0)
        binding.weekSpinner.onItemSelectedListener = this

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

        navigator.navigateToDetail(id, awayTeam, homeTeam)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bag.clear()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        viewModel.filterWeek.onNext(position + 1)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

}
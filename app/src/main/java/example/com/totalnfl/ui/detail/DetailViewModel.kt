package example.com.totalnfl.ui.detail

import dagger.hilt.android.lifecycle.HiltViewModel
import example.com.totalnfl.arch.BaseViewModel
import example.com.totalnfl.network.TotalNflService
import javax.inject.Inject

@HiltViewModel
class DetailViewModel  @Inject constructor(
        private var totalNflService: TotalNflService
): BaseViewModel() {






}
package example.com.totalnfl.navigator

import android.app.Activity
import androidx.navigation.Navigation
import example.com.totalnfl.R
import example.com.totalnfl.ui.list.ListFragmentDirections
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(
    private val activity: Activity
) : AppNavigator {

    private val navController by lazy {
        Navigation.findNavController(activity, R.id.nav_host_fragment)
    }

    override fun navigateToDetail(id: Long, awayTeam: String, homeTeam: String) {
        navController.navigate(ListFragmentDirections.actionListFragmentToDetailFragment(id, awayTeam, homeTeam))
    }

    override fun popBackStack() {
        navController.popBackStack()
    }
}